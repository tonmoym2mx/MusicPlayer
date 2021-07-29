package com.tonmoy.gakk.meow.musicplayer.player

import android.content.ComponentName
import android.content.Context
import android.media.browse.MediaBrowser
import android.media.session.PlaybackState
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
    var transportControls:MediaControllerCompat.TransportControls? = null
    val currentPlayingSong: MutableLiveData<Song> by lazy { MutableLiveData<Song>() }
    val playbackState: MutableLiveData<PlaybackStateCompat> by lazy { MutableLiveData<PlaybackStateCompat>() }
    val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        MediaBrowserConnectionCallback(),
        null
    ).apply { connect() }

    fun disconnect(){
        mediaBrowser.disconnect()
    }


    inner class MediaBrowserConnectionCallback : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            mediaControllerCompat = MediaControllerCompat(context,mediaBrowser.sessionToken)
            transportControls = mediaControllerCompat.transportControls
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
            currentPlayingSong.postValue(metadata?.toSong().apply {  })
        }
    }



}