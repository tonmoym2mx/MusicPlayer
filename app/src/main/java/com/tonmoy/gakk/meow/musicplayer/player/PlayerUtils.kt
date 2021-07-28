package com.tonmoy.gakk.meow.musicplayer.player

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import org.koin.java.KoinJavaComponent.get


fun List<Song>.toServiceMediaItemList() = this.map { song -> song.toServiceMediaItem() }
fun List<Song>.toServiceMediaItemMutableList() = this.toServiceMediaItemList().toMutableList()
fun List<Song>.toDataSource(): ConcatenatingMediaSource {
    val factory: DefaultDataSourceFactory = get(DefaultDataSourceFactory::class.java)
    val concatenatingMediaSource = ConcatenatingMediaSource()
    val sources = this.map { song ->
        ProgressiveMediaSource.Factory(factory)
            .createMediaSource(song.toPlayerMediaItem())
    }.toList()
    concatenatingMediaSource.addMediaSources(sources)
    return concatenatingMediaSource
}
fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(id = it.mediaId?.toInt(),
            title =  it.title.toString(),
            subTitle = it.subtitle.toString(),
            thumbnail = it.iconUri.toString(),
            songUrl = it.mediaUri.toString())
    }
}
fun MediaBrowserCompat.MediaItem.toSong() = Song(
    id = mediaId?.toInt(),
    title = description.title.toString(),
    subTitle = description.subtitle.toString(),
    thumbnail =  description.iconUri.toString(),
    songUrl = description.mediaUri.toString()
)

fun List<MediaBrowserCompat.MediaItem>.toSongList() = this.map { mediaItem -> mediaItem.toSong() }



fun bitmapFromUri(context:Context,imageUrl:Uri,onBitmap:(Bitmap)->Unit) {
    Glide.with(context).asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                onBitmap(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) = Unit
        })
}


inline val PlaybackStateCompat.isPrepare:Boolean
    get() = state == PlaybackStateCompat.STATE_BUFFERING ||
            state == PlaybackStateCompat.STATE_PLAYING ||
            state == PlaybackStateCompat.STATE_PAUSED


inline val PlaybackStateCompat.isPlaying
    get() = state == PlaybackStateCompat.STATE_BUFFERING ||
            state == PlaybackStateCompat.STATE_PLAYING






