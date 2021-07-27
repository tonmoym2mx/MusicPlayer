package com.tonmoy.gakk.meow.musicplayer.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tonmoy.gakk.meow.musicplayer.R
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.ui.adapter.SongAdapter


@BindingAdapter("nullableText")
fun TextView.nullableText(text:String?=null){
    this.text = text ?: "--"
}
/*@BindingAdapter("submitVideo")
fun RecyclerView.submitVideo(list:List<Video>?=null){
    if(this.adapter is VideoRecyclerAdapter && list != null){
        (this.adapter as VideoRecyclerAdapter).submitList(list)
    }
}*/
@BindingAdapter("songsList")
fun RecyclerView.songsList(list:List<Song>?=null){
    if(this.adapter is SongAdapter && list != null){
        (this.adapter as SongAdapter).submitList(list)
    }
}
@BindingAdapter("currentSong")
fun RecyclerView.currentSong(song:Song?=null){
    if(this.adapter is SongAdapter && song != null){
        (this.adapter as SongAdapter).currentSong(song)
    }
}
@BindingAdapter("isShowProgressBar")
fun ProgressBar.isShowProgressBar(isShow:Boolean?=null){
    val visibility = if (isShow == true) View.VISIBLE else View.GONE
    this.visibility = visibility
}
@BindingAdapter("setImage")
fun ImageView.setImage(image:Int?=null){
    image?.let { this.setImageResource(it) }
}
@BindingAdapter("imageUrl")
fun ImageView.imageUrl(imageUrl:String?=null){
    Glide.with(this.context)
        .load(imageUrl)
        .apply( RequestOptions().override(500, 500))
        .placeholder(R.drawable.music_placeholder)
        .into(this)
}
@BindingAdapter("isPlaying")
fun TextView.isPlaying(isPlaying:Boolean?=null){
    if(isPlaying== true){
        this.setTextColor(context.getColor(R.color.green))
    }else{
        this.setTextColor(context.getColor(R.color.text1_color))
    }
}


/*
@BindingAdapter("playVideo")
fun PlayerView.playVideo(video: Video?=null){
    if(video!=null) {
        player?.clearMediaItems()
        player?.setMediaItem(video.toMediaItem())
        player?.prepare()
        player?.playWhenReady = true
    }
}*/
