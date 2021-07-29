package com.tonmoy.gakk.meow.musicplayer.player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig.COMMON_KEY
import com.tonmoy.gakk.meow.musicplayer.data.model.MusicPosition
import com.tonmoy.gakk.meow.musicplayer.data.model.Song

private const val TAG = "MusicServiceConnection"

class MusicServiceConnection(private val context: Context) {
    val musicPositionLiveData: MutableLiveData<MusicPosition> by lazy { MutableLiveData<MusicPosition>() }

    private var mediaControllerCompat: MediaControllerCompat?=null
    var transportControls:MediaControllerCompat.TransportControls? = null
    private val mediaControllerCallback:MediaControllerCallback  =  MediaControllerCallback()
    val currentPlayingSong: MutableLiveData<Song?> by lazy { MutableLiveData<Song?>(null) }
    val playbackState: MutableLiveData<PlaybackStateCompat> by lazy { MutableLiveData<PlaybackStateCompat>() }
    val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        MediaBrowserConnectionCallback(),
        null
    ).apply {
        connect()
    }

    fun disconnect(){
        mediaControllerCompat?.unregisterCallback(mediaControllerCallback)
        mediaBrowser.disconnect()

    }
    fun requestForNewMusicPosition(){
        sendCommand(MusicServiceCommand.MUSIC_POSITION,null)
    }

    fun sendCommand(musicServiceCommand:MusicServiceCommand,bundle: Bundle?){
        mediaControllerCompat?.sendCommand(musicServiceCommand.command,bundle,MusicResultReceiver())
    }

    inner class MusicResultReceiver:ResultReceiver(Handler(Looper.getMainLooper())){
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            when(resultCode){
                MusicServiceCommand.MUSIC_POSITION.requestCode ->{
                    val serializable = resultData?.getSerializable(COMMON_KEY)
                    if( serializable is MusicPosition){
                        musicPositionLiveData.postValue(serializable)
                    }

                }
            }

        }
    }

    inner class MediaBrowserConnectionCallback : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            mediaControllerCompat = MediaControllerCompat(context,mediaBrowser.sessionToken)
            transportControls = mediaControllerCompat?.transportControls
            mediaControllerCompat?.registerCallback(mediaControllerCallback)


        }
    }
    inner class MediaControllerCallback: MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            currentPlayingSong.postValue(metadata?.toSong().apply {  })
        }
    }

    enum class MusicServiceCommand(val requestCode:Int,val command:String){
        MUSIC_POSITION(123,"player_position")
    }

}
