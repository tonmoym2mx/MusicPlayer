package com.tonmoy.gakk.meow.musicplayer.player

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.tonmoy.gakk.meow.musicplayer.data.model.Song

class MusicQueueNavigator(sessionCompat: MediaSessionCompat,private val songs:List<Song>):TimelineQueueNavigator(sessionCompat) {
    override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
        return songs.toServiceMediaItemList()[windowIndex].description
    }
}