package com.tonmoy.gakk.meow.musicplayer.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.tonmoy.gakk.meow.musicplayer.R

class MusicApp :Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()


    }
    private fun createNotificationChannels() {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    getString(R.string.CHANNEL_ID),
                    getString(R.string.CHANNEL_NAME),
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = getString(R.string.Description)
                val manager = getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            }
     }
}

