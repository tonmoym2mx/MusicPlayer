package com.tonmoy.gakk.meow.musicplayer.ui.activity

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tonmoy.gakk.meow.musicplayer.data.model.MusicPosition
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection
import com.tonmoy.gakk.meow.musicplayer.player.isPlaying
import com.tonmoy.gakk.meow.musicplayer.player.isPrepare
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivityViewModel( val connection: MusicServiceConnection,val player: SimpleExoPlayer):ViewModel() {
    val currentSong: MutableLiveData<Song?> = connection.currentPlayingSong
    val playbackStatus: MutableLiveData<PlaybackStateCompat> = connection.playbackState
    val musicPositionLiveData: MutableLiveData<MusicPosition>  = connection.musicPositionLiveData
    var shouldUpdateSeekbar = true
    fun toggleSong(){
        if(isPrepare()){
            if(isPlaying()){
                connection.transportControls?.pause()
            }else{
                connection.transportControls?.play()
            }
        }
    }
    fun skipNext() = connection.transportControls?.skipToNext()
    fun skipToPrevious() = connection.transportControls?.skipToPrevious()
    private fun isPlaying() = connection.playbackState.value?.isPlaying?:false
    private fun isPrepare() = connection.playbackState.value?.isPrepare?:false

    fun updatePlayerPosition() = viewModelScope.launch(Dispatchers.IO) {
        while(true){
            if(shouldUpdateSeekbar){
                connection.requestForNewMusicPosition()
            }
            delay(100)
        }
    }
   /* private fun positionFlow() = flow {
        while (true){
            if(shouldUpdateSeekbar) {
                val position = MusicPosition(
                    currentPosition = player.currentPosition,
                    bufferPosition = player.bufferedPosition,
                    duration = player.bufferedPosition
                )
                emit(position)
            }
            kotlinx.coroutines.delay(100)
        }
    }*/

    fun updatePlayerPosition(toMusicPosition: MusicPosition?) {
        musicPositionLiveData.postValue(toMusicPosition)
    }

    fun seekTo(musicPosition: MusicPosition) {
        connection.transportControls?.seekTo(musicPosition.currentPosition?:0)
    }


}