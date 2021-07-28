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
    single { exoPlayer(get()) }
    single { dataFactory(get()) }
    single { SongFakeApi() }
    single { MusicServiceConnection(get()) }

}
val viewModelModule = module {
    viewModel { MusicListViewModel(get()) }
    viewModel { MainActivityViewModel(get(),get()) }
}