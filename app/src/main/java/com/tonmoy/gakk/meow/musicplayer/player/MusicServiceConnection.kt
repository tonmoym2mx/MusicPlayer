package com.tonmoy.gakk.meow.musicplayer.player

import android.content.ComponentName
import android.content.Context
import android.media.browse.MediaBrowser
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "MusicServiceConnection"
class MusicServiceConnection(private val context: Context) {
    private lateinit var mediaControllerCompat: MediaControllerCompat
    private  var subscriptionCallback:SubscriptionCallback = SubscriptionCallback()
    val songListLiveData: MutableLiveData<List<Song>> by lazy { MutableLiveData<List<Song>>() }
    val currentPlayingSong: MutableLiveData<Song> by lazy { MutableLiveData<Song>() }
    val playbackState: MutableLiveData<PlaybackStateCompat> by lazy { MutableLiveData<PlaybackStateCompat>() }
    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        MediaBrowserConnectionCallback(),
        null
    ).apply { connect() }
    fun subscribe(parentId:String){
        mediaBrowser.subscribe(parentId,subscriptionCallback)
    }
    fun unsubscribe(parentId:String){
        mediaBrowser.unsubscribe(parentId,subscriptionCallback)
    }
    fun disconnect(){
        mediaBrowser.disconnect()
    }
    inner class SubscriptionCallback: MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
            songListLiveData.postValue(children.toSongList())
        }

    }

    inner class MediaBrowserConnectionCallback : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            mediaControllerCompat = MediaControllerCompat(context,mediaBrowser.sessionToken)
            mediaControllerCompat.registerCallback(MediaControllerCallback())

        }
    }
    inner class MediaControllerCallback: MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            currentPlayingSong.postValue(metadata?.toSong())
        }
    }



}