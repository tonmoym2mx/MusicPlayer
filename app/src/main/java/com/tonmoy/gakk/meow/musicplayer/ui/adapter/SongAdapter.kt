package com.tonmoy.gakk.meow.musicplayer.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.databinding.RowSongBinding
import com.tonmoy.gakk.meow.musicplayer.utils.isPlayingAnimation
import java.lang.Exception

typealias SongSelectListener = (Song)->Unit
class SongAdapter: ListAdapter<Song,SongAdapter.SongViewHolder>(SongDiffCallBack()){
    private var songSelectListener:SongSelectListener?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.mainContainer.setOnClickListener {
            songSelectListener?.invoke(item)
        }

    }
    fun setOnSongSelectListener(songSelectListener:SongSelectListener?){
        this.songSelectListener = songSelectListener
    }
    fun isPlaying( isMusicPlaying:Boolean = false){
       /* if(isMusicPlaying){
            val song = currentList.first{song -> song.isPrepare == true }

            val new = newListPlay(currentList,song.copy(isPlaying = true))
            Log.i("Play", "isPlaying: ${new.map { "${it.id} -> ${it.isPlaying}" }}")
            submitList(new)
        }*/

    }
    fun currentSong(cSong: Song){
        submitList(newList(currentList,cSong))
    }
    private fun newList(oldList:List<Song>, currentSong:Song): List<Song> {
        val newlist:MutableList<Song> = ArrayList()
        oldList.forEach { oldSong ->
            newlist.add(oldSong.copy(isPrepare = oldSong.id == currentSong.id))
        }
        return newlist
    }
    private fun newListPlay(oldList:List<Song>, currentSong:Song): List<Song> {
        val newlist:MutableList<Song> = ArrayList()
        oldList.forEach { oldSong ->
            newlist.add(oldSong.copy(isPlaying = oldSong.id == currentSong.id))
        }
        return newlist
    }
    class SongViewHolder private constructor(val binding: RowSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Song){
            binding.song = item
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent:ViewGroup): SongViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding:RowSongBinding  = RowSongBinding.inflate(layoutInflater,parent,false)
                return SongViewHolder(binding)
            }
        }
    }
}
class  SongDiffCallBack: DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
       return oldItem == newItem
    }

}