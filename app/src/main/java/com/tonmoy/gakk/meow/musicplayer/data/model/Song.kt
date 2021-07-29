package com.tonmoy.gakk.meow.musicplayer.data.model

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import java.io.Serializable

data class Song(
    var id:Int? = null,
    var title:String? = null,
    var subTitle:String? = null,
    var thumbnail:String? = null,
    var songUrl:String?= null,
    var isPrepare:Boolean? = false,
    var isPlaying:Boolean? = false
):Serializable{
    fun toPlayerMediaItem() = MediaItem.Builder()
        .setUri(songUrl)
        .setMediaId(id.toString())
        .build()

    fun toServiceMediaItem(): MediaBrowserCompat.MediaItem {
        val description= MediaDescriptionCompat.Builder()
            .setMediaUri(songUrl?.toUri())
            .setTitle(title)
            .setSubtitle(subTitle)
            .setMediaId(id.toString())
            .setIconUri(thumbnail?.toUri())
            .build()
        return MediaBrowserCompat.MediaItem(description,MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }

}

