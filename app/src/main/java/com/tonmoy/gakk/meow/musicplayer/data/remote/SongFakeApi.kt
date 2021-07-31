package com.tonmoy.gakk.meow.musicplayer.data.remote

import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import kotlinx.coroutines.delay

class SongFakeApi {
    suspend fun fetchSongs(): List<Song> {

        delay(300)
        return listOf(
            Song(
                id = 0,
                title = "A Place For My Head",
                subTitle = "A Place For My Head - Linkin Park (Hybrid Theory)",
                thumbnail = "https://m.media-amazon.com/images/M/MV5BNzc1MjBiM2QtNTcxNS00YWQ2LTgwMDktOGYxMTU5M2Q4ZjBhXkEyXkFqcGdeQXVyNDQ5MDYzMTk@._V1_.jpg",

                songUrl = "https://tonmoy.info/development/music/A%20Place%20For%20My%20Head%20-%20Linkin%20Park%20(Hybrid%20Theory).mp3"
            ),
            Song(
                id = 1,
                title = "In The End",
                subTitle = "In The End [Official HD Music Video] - Linkin Park ",
                thumbnail = "https://i1.sndcdn.com/artworks-000234675739-6wl1gi-t500x500.jpg",
                songUrl = "https://tonmoy.info/development/music/In%20The%20End%20[Official%20HD%20Music%20Video]%20-%20Linkin%20Park%20(1).mp3"
            ),
            Song(
                id = 2,
                title = "LOST IN THE ECHO ",
                subTitle = "Linkin Park-LOST IN THE ECHO [Official Music Video] ",
                thumbnail = "https://wallpaperaccess.com/full/341785.jpg",
                songUrl = "https://tonmoy.info/development/music/LOST%20IN%20THE%20ECHO%20[Official%20Music%20Video]%20-%20Linkin%20Park.mp3"),
            Song(
                id = 3,
                title = "Pushing Me Away",
                subTitle = "Linkin Park (Hybrid Theory) - Pushing Me Away",
                thumbnail = "https://themepack.me/i/c/749x467/media/g/1310/linkin-park-theme-lq16.jpg",
                songUrl = "https://tonmoy.info/development/music/Pushing%20Me%20Away%20-%20Linkin%20Park%20(Hybrid%20Theory).mp3"
            ),
            Song(
                id = 4,
                title = "What I've Done",
                subTitle = "Linkin Park - What I've Done [Official Music Video] ",
                thumbnail = "https://c4.wallpaperflare.com/wallpaper/201/444/964/hybrid-linkin-park-wallpaper-thumb.jpg",
                songUrl = "https://tonmoy.info/development/music/What%20I've%20Done%20[Official%20Music%20Video]%20-%20Linkin%20Park.mp3"
            ),
            Song(
                id = 10,
                title = "Cat Music",
                subTitle = "Meow - Cat Music",
                thumbnail = "https://cdn.britannica.com/22/206222-131-E921E1FB/Domestic-feline-tabby-cat.jpg",
                songUrl = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3"
            )



        )
    }


}