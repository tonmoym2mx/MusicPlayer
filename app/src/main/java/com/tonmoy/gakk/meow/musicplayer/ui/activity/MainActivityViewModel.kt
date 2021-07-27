package com.tonmoy.gakk.meow.musicplayer.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection

class MainActivityViewModel(val connection: MusicServiceConnection):ViewModel() {
    val songListLiveData: MutableLiveData<List<Song>> = connection.songListLiveData
    val currentSong: MutableLiveData<Song> = connection.currentPlayingSong

}