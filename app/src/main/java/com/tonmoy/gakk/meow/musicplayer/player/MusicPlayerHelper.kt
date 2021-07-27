package com.tonmoy.gakk.meow.musicplayer.player

import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.tonmoy.gakk.meow.musicplayer.config.AppConfig


private fun audioAttributes() = AudioAttributes.Builder()
    .setContentType(C.CONTENT_TYPE_MUSIC)
    .setUsage(C.USAGE_MEDIA)
    .build()
fun exoPlayer(context: Context) =  SimpleExoPlayer.Builder(context)
    .setAudioAttributes(audioAttributes(),true)
    .setHandleAudioBecomingNoisy(true)
    .build()
fun dataFactory(context: Context) = DefaultDataSourceFactory(context, Util.getUserAgent(context, AppConfig.MEDIA_SESSION_TAG))

