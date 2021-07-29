package com.tonmoy.gakk.meow.musicplayer.player

import androidx.lifecycle.MutableLiveData
import com.tonmoy.gakk.meow.musicplayer.data.model.MusicPosition

class PlayerPositionCallback {
    val musicPositionLiveData: MutableLiveData<MusicPosition> by lazy { MutableLiveData<MusicPosition>() }
}