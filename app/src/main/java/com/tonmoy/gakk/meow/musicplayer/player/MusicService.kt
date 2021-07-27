package com.tonmoy.gakk.meow.musicplayer.player

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import androidx.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig.MEDIA_SESSION_TAG
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.data.remote.SongFakeApi
import com.tonmoy.gakk.meow.musicplayer.ui.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)
        session = MediaSessionCompat(this, MEDIA_SESSION_TAG).apply {
            setSessionActivity(pendingIntent)
            isActive = true
        }
        sessionToken = session.sessionToken
        mediaSessionConnector = MediaSessionConnector(session)
        mediaSessionConnector.setPlayer(exoPlayer)
        mediaSessionConnector.setQueueNavigator(MusicQueueNavigator(session,songs))
        musicNotificationManager = MusicNotificationManager(this,session.sessionToken,
        MusicPlayerNotificationListener(this))
        musicNotificationManager.showNotification(exoPlayer)

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }
    override fun onDestroy() {
        session.release()
        exoPlayer.release()
        serviceScope.cancel()
    }

    override fun onGetRoot(clientPackageName: String,
                           clientUid: Int,
                           rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        return MediaBrowserServiceCompat.BrowserRoot("root", null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaItem>>) {
        fetchSongs()

        result.sendResult(songs.toServiceMediaItemMutableList())
        preparePlayer(true)
    }
    private fun fetchSongs(){
        songs.clear()
        songs.addAll(songApi.fetchSongs())
        /*serviceScope.launch {

        }*/
    }
    private fun preparePlayer(isPlay:Boolean){
        exoPlayer.setMediaSource(songs.toDataSource())
        exoPlayer.prepare()
        exoPlayer.playWhenReady = isPlay
    }

}