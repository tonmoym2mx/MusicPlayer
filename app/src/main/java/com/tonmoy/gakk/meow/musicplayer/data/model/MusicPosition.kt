package com.tonmoy.gakk.meow.musicplayer.data.model

import java.io.Serializable

data class MusicPosition(
    val currentPosition:Long?=0,
    val bufferPosition:Long?=0,
    val duration:Long?=0,
):Serializable{
    fun durationTimeLabel() = createTimeLabel(duration?:0)
    fun currentPositionTimeLabel() = createTimeLabel(currentPosition?:0)
    fun createTimeLabel(duration: Long): String? {
        var timeLabel: String? = ""
        val min = duration / 1000 / 60
        val sec = duration / 1000 % 60
        timeLabel += "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }
}
