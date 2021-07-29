package com.tonmoy.gakk.meow.musicplayer.player

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig.COMMON_KEY
import com.tonmoy.gakk.meow.musicplayer.data.model.MusicPosition
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection.MusicServiceCommand

class MusicPlaybackPreparer(private val player: SimpleExoPlayer,private val currentPlaySong:(Song)->Unit):MediaSessionConnector.PlaybackPreparer {
    override fun onCommand(
        player: Player,
        controlDispatcher: ControlDispatcher,
        command: String,
        extras: Bundle?,
        cb: ResultReceiver?
    ): Boolean {

        when (command){
            MusicServiceCommand.MUSIC_POSITION.command ->{
                val musicPosition = MusicPosition(
                    currentPosition = player.currentPosition,
                    bufferPosition = player.bufferedPosition,
                    duration = player.duration
                )
                val bundle = Bundle().apply {
                    putSerializable(COMMON_KEY,musicPosition)
                }
                cb?.send(MusicServiceCommand.MUSIC_POSITION.requestCode,bundle)
            }

        }
        return true
    }

    override fun getSupportedPrepareActions(): Long {
        return PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
    }

    override fun onPrepare(playWhenReady: Boolean) {

    }

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
        currentPlaySong(Song(id = mediaId.toInt()))
    }

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {

    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) {

    }
}