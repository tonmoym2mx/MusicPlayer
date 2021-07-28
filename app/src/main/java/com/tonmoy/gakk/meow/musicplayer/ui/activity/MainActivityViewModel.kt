package com.tonmoy.gakk.meow.musicplayer.ui.activity

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tonmoy.gakk.meow.musicplayer.data.model.PlayerPosition
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection
import com.tonmoy.gakk.meow.musicplayer.player.isPlaying
import com.tonmoy.gakk.meow.musicplayer.player.isPrepare
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivityViewModel( val connection: MusicServiceConnection,val player: SimpleExoPlayer):ViewModel() {
    val currentSong: MutableLiveData<Song> = connection.currentPlayingSong
    val playbackStatus: MutableLiveData<PlaybackStateCompat> = connection.playbackState
    val playerPosition: MutableLiveData<PlayerPosition> by lazy { MutableLiveData<PlayerPosition>() }
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

    fun updatePlayerPosition() = viewModelScope.launch {
        positionFlow().collect { value ->
            playerPosition.postValue(value)
        }
    }
    private fun positionFlow() = flow {
        while (true){
            if(shouldUpdateSeekbar) {
                val position = PlayerPosition(
                    currentPosition = player.currentPosition,
                    bufferPosition = player.bufferedPosition,
                    duration = player.duration
                )
                emit(position)
            }
            kotlinx.coroutines.delay(100)
        }
    }

    fun updatePlayerPosition(toPlayerPosition: PlayerPosition?) {
        playerPosition.postValue(toPlayerPosition)
    }

    fun seekTo(playerPosition: PlayerPosition) {
        connection.transportControls?.seekTo(playerPosition.currentPosition?:0)
    }
}