package com.tonmoy.gakk.meow.musicplayer.utils

import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tonmoy.gakk.meow.musicplayer.R
import com.tonmoy.gakk.meow.musicplayer.data.model.MusicPosition
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.isPlaying
import com.tonmoy.gakk.meow.musicplayer.player.isPrepare
import com.tonmoy.gakk.meow.musicplayer.ui.adapter.SongAdapter


@BindingAdapter("nullableText")
fun TextView.nullableText(text:String?=null){
    this.text = text ?: ""
}
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
        this.setTextColor(context.getColor(R.color.white))
    }else{
        this.setTextColor(context.getColor(R.color.text1_color))
    }
}
@BindingAdapter("isPlayingCard")
fun CardView.isPlayingCard(isPlaying:Boolean?=null){
    if(isPlaying== true){
        this.setCardBackgroundColor(context.getColor(R.color.card_color))
    }else{
        this.setCardBackgroundColor(context.getColor(android.R.color.transparent))
    }
}

@BindingAdapter("isPrepareAnimation")
fun LottieAnimationView.isPrepareAnimation(isPrepare:Boolean?=null){
    if(isPrepare== true){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}
@BindingAdapter("isPlayingAnimation")
fun LottieAnimationView.isPlayingAnimation(isPlaying:Boolean?=null){
    if(isPlaying== true){
        this.visibility = View.VISIBLE
        this.playAnimation()
    }else{
        this.visibility = View.GONE
    }
}
@BindingAdapter("playbackStatus")
fun ImageView.playbackStatus(playbackStatus:PlaybackStateCompat?=null){
    if(playbackStatus?.isPrepare == true) {
        this.isEnabled = true
        if (playbackStatus.isPlaying) {
            this.setImageResource(R.drawable.ic_baseline_pause_24)
        } else {
            this.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }
    }else{
        this.isEnabled = false
    }
}

@BindingAdapter("musicPosition")
fun SeekBar.playerPosition(musicPosition:MusicPosition?=null){
    if(musicPosition!=null){
        if(musicPosition.duration?:0 >=0){
            this.max = musicPosition.duration?.toInt()?:0
        }
        if(musicPosition.currentPosition?:0 >=0){
            this.progress = musicPosition.currentPosition?.toInt()?:0
        }
        if(musicPosition.bufferPosition?:0 >=0){
            this.secondaryProgress = musicPosition.bufferPosition?.toInt()?:0
        }


    }
}
@BindingAdapter("durationTime")
fun TextView.durationTime(musicPosition:MusicPosition?=null){
    musicPosition?.let {
        if(it.duration?:0>=0) {
            this.text = it.durationTimeLabel()
        }
    }
}
@BindingAdapter("currentTime")
fun TextView.currentTime(musicPosition:MusicPosition?=null){
    musicPosition?.let {
        if(it.currentPosition?:0>=0) {
            this.text = it.currentPositionTimeLabel()
        }
    }
}
@BindingAdapter("isLoading")
fun ProgressBar.isLoading(isLoading: Boolean?=null){
    if(isLoading == true){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}
fun SeekBar.toPlayerPosition(): MusicPosition = MusicPosition(currentPosition = this.progress.toLong(),
     bufferPosition = this.secondaryProgress.toLong(),
     duration = this.max.toLong())



