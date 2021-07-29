package com.tonmoy.gakk.meow.musicplayer.ui.fragment.music

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.Toast
import androidx.lifecycle.*
import com.google.android.exoplayer2.Player
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection
import com.tonmoy.gakk.meow.musicplayer.player.toSongList
import kotlinx.coroutines.flow.flow

class MusicListViewModel(private val musicServiceConnection: MusicServiceConnection) : ViewModel() {

     val playbackStatus: MutableLiveData<PlaybackStateCompat> = musicServiceConnection.playbackState
     private val subscriptionCallback:SubscriptionCallback = SubscriptionCallback()
     val songListLiveData: MutableLiveData<List<Song>>  by lazy { MutableLiveData<List<Song>>() }
     val currentSong: MutableLiveData<Song> = musicServiceConnection.currentPlayingSong
     val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }


     fun subscribe(){
          isLoading.postValue(true)
          musicServiceConnection.mediaBrowser.subscribe("root",subscriptionCallback)
     }
     private fun unSubscribe(){
          musicServiceConnection.mediaBrowser.unsubscribe("root",subscriptionCallback)
     }
     fun changeMusic(song: Song){
          musicServiceConnection.transportControls?.playFromMediaId(song.id.toString(),null)
     }
     override fun onCleared() {
          super.onCleared()
         unSubscribe()
     }
     inner class SubscriptionCallback: MediaBrowserCompat.SubscriptionCallback() {
          override fun onChildrenLoaded(
               parentId: String,
               children: MutableList<MediaBrowserCompat.MediaItem>
          ) {
               super.onChildrenLoaded(parentId, children)
               songListLiveData.postValue(children.toSongList())
               isLoading.postValue(false)
          }

     }
}