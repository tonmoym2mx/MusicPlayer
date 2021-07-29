package com.tonmoy.gakk.meow.musicplayer.di

import com.tonmoy.gakk.meow.musicplayer.data.remote.SongFakeApi
import com.tonmoy.gakk.meow.musicplayer.player.MusicServiceConnection
import com.tonmoy.gakk.meow.musicplayer.player.dataFactory
import com.tonmoy.gakk.meow.musicplayer.player.exoPlayer
import com.tonmoy.gakk.meow.musicplayer.ui.activity.MainActivityViewModel
import com.tonmoy.gakk.meow.musicplayer.ui.fragment.music.MusicListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { exoPlayer(get()) }
    factory { dataFactory(get()) }
    factory { SongFakeApi() }
    factory { MusicServiceConnection(get()) }

}
val viewModelModule = module {
    viewModel { MusicListViewModel(get()) }
    viewModel { MainActivityViewModel(get(),get()) }
}