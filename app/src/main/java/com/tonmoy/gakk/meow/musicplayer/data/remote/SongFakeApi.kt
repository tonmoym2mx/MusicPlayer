package com.tonmoy.gakk.meow.musicplayer.data.remote

import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import kotlinx.coroutines.delay

class SongFakeApi {
     fun fetchSongs(): List<Song> {

        return listOf(
            Song(
                id = 0,
                title = "In The End",
                subTitle = "Linkin Park - In The End",
                thumbnail = "https://cdn.britannica.com/22/206222-131-E921E1FB/Domestic-feline-tabby-cat.jpg",
                songUrl = "https://firebasestorage.googleapis.com/v0/b/exoplayerdemo-aea95.appspot.com/o/Linkin%20Park%20-%20In%20The%20End%20(Mellen%20Gi%20%26%20Tommee%20Profitt%20Remix).mp3?alt=media&token=0479361c-7101-4f74-bc17-d1f483cee48b"
            ),
            Song(
                id = 1,
                title = "In The End 1",
                subTitle = "Linkin Park - In The End",
                thumbnail = "https://cdn.britannica.com/22/206222-131-E921E1FB/Domestic-feline-tabby-cat.jpg",
                songUrl = "https://firebasestorage.googleapis.com/v0/b/exoplayerdemo-aea95.appspot.com/o/Linkin%20Park%20-%20In%20The%20End%20(Mellen%20Gi%20%26%20Tommee%20Profitt%20Remix).mp3?alt=media&token=0479361c-7101-4f74-bc17-d1f483cee48b"
            ),
            Song(
                id = 2,
                title = "In The End 2",
                subTitle = "Linkin Park - In The End",
                thumbnail = "https://cdn.britannica.com/22/206222-131-E921E1FB/Domestic-feline-tabby-cat.jpg",
                songUrl = "https://firebasestorage.googleapis.com/v0/b/exoplayerdemo-aea95.appspot.com/o/Linkin%20Park%20-%20In%20The%20End%20(Mellen%20Gi%20%26%20Tommee%20Profitt%20Remix).mp3?alt=media&token=0479361c-7101-4f74-bc17-d1f483cee48b"
            )
        )
    }


}