package com.tonmoy.gakk.meow.musicplayer.player

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import androidx.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig.MEDIA_SESSION_TAG
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.data.remote.SongFakeApi
import com.tonmoy.gakk.meow.musicplayer.ui.activity.MainActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

import java.util.ArrayList

class MusicService : MediaBrowserServiceCompat() {
    private lateinit var session: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector
    private lateinit var musicNotificationManager: MusicNotificationManager
    private val exoPlayer:SimpleExoPlayer by inject()
    private val serviceScope:CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val songApi:SongFakeApi by inject()
    var isForegroundService = false
    private val songs:MutableList<Song> = ArrayList()


    override fun onCreate() {
        super.onCreate()
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, 0)
        }
        session = MediaSessionCompat(this, MEDIA_SESSION_TAG).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }
        val stateBuilder = PlaybackStateCompat.Builder()
            .setActions(PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
        session.setPlaybackState(stateBuilder.build())

        sessionToken = session.sessionToken

        mediaSessionConnector = MediaSessionConnector(session)
        mediaSessionConnector.setPlayer(exoPlayer)
        mediaSessionConnector.setQueueNavigator(MusicQueueNavigator(session,songs))



        val musicPlaybackPreparer = MusicPlaybackPreparer(exoPlayer){
            changeSong(it)
        }
        mediaSessionConnector.setPlaybackPreparer(musicPlaybackPreparer)
        musicNotificationManager = MusicNotificationManager(this,session.sessionToken,
        MusicPlayerNotificationListener(this))
        exoPlayer.let { musicNotificationManager.showNotification(it) }

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop(true)
    }
    override fun onDestroy() {
        session.release()
        exoPlayer.release()
        serviceScope.cancel()
    }

    override fun onGetRoot(clientPackageName: String,
                           clientUid: Int,
                           rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        return BrowserRoot("root", null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaItem>>) {
        result.detach()
        fetchSongs(){
            result.sendResult(songs.toServiceMediaItemMutableList())
            preparePlayer(false)
        }

    }
    private fun fetchSongs(onReady:()->Unit){
        serviceScope.launch {
            songs.clear()
            songs.addAll(songApi.fetchSongs())
            withContext(Dispatchers.Main){
                onReady()
            }

        }
    }
    private fun preparePlayer(isPlay:Boolean){
        exoPlayer.setMediaSource(songs.toDataSource())
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    private fun changeSong(ssong:Song){
        val index = songs.indexOfFirst { song -> song.id == ssong.id}
        exoPlayer.seekTo(index,0L)
        exoPlayer.playWhenReady = true
    }
}