package com.tonmoy.gakk.meow.musicplayer.ui.fragment.music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tonmoy.gakk.meow.musicplayer.databinding.MusicListFragmentBinding
import com.tonmoy.gakk.meow.musicplayer.ui.adapter.SongAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class MusicListFragment : Fragment() {

    companion object {
        fun newInstance() = MusicListFragment()
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel: MusicListViewModel by viewModel()
    private lateinit var binding:MusicListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MusicListFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
        setupAdapter()
        viewModel.subscribe()
    }

    private fun setupAdapter() {
        songAdapter = SongAdapter()
        binding.songRecyclerView.adapter = songAdapter
        songAdapter.setOnSongSelectListener {
            viewModel.changeMusic(it)
        }
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }


}