package com.tonmoy.gakk.meow.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tonmoy.gakk.meow.musicplayer.data.model.Song
import com.tonmoy.gakk.meow.musicplayer.databinding.RowSongBinding

class SongAdapter: ListAdapter<Song,SongAdapter.SongViewHolder>(SongDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    fun currentSong(cSong: Song){
        submitList(newList(currentList,cSong))
    }
    private fun newList(oldList:List<Song>, currentSong:Song): List<Song> {
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
        println("New "+newItem)
        println("Old "+oldItem)
       return oldItem == newItem
        //return false
    }

}