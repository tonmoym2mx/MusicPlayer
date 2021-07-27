package com.tonmoy.gakk.meow.musicplayer.player

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.tonmoy.gakk.meow.musicplayer.R
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig.NOTIFICATION_ID

class MusicNotificationManager(
    private val context: Context,
    private val token:MediaSessionCompat.Token,
    private val notificationListener:PlayerNotificationManager.NotificationListener
) {
    private lateinit var notificationManager: PlayerNotificationManager
    private var mediaControllerCompat: MediaControllerCompat = MediaControllerCompat(context,token)

    init {
        notificationManager = PlayerNotificationManager.Builder(
            context,
            NOTIFICATION_ID,
            context.getString(R.string.CHANNEL_ID),
            MusicMediaDescriptionAdapter(),
        )
            .apply { this.setNotificationListener(notificationListener) }
            .build().apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setMediaSessionToken(token)
            }
    }
    fun showNotification(player: Player) {
        notificationManager.setPlayer(player)
    }

    inner  class  MusicMediaDescriptionAdapter : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaControllerCompat.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return  mediaControllerCompat.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return mediaControllerCompat.metadata.description.subtitle
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val uri = mediaControllerCompat.metadata.description.iconUri
            if (uri != null) {
                bitmapFromUri(context,uri){
                    callback.onBitmap(it)
                }
            }
            return null
        }

    }
}