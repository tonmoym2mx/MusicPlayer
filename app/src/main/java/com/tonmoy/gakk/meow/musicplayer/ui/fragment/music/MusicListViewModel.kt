package com.tonmoy.gakk.meow.musicplayer.ui.fragment.music

import androidx.lifecycle.*
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection

class MusicListViewModel(val musicServiceConnection: MusicServiceConnection) : ViewModel() {

     val songListLiveData: MutableLiveData<List<Song>>  = musicServiceConnection.songListLiveData
     val currentSong: MutableLiveData<Song> = musicServiceConnection.currentPlayingSong




}