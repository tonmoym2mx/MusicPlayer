package com.tonmoy.gakk.meow.musicplayer.ui.activity

import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tonmoy.gakk.meow.musicplayer.R
import com.tonmoy.gakk.meow.musicplayer.databinding.ActivityMainBinding
import com.tonmoy.gakk.meow.musicplayer.player.MusicService
import com.tonmoy.gakk.meow.musicplayer.utils.toPlayerPosition
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel:MainActivityViewModel by viewModel()
    private val player:SimpleExoPlayer by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupUi()
        viewModel.updatePlayerPosition()
    }
    private fun setupUi() {
        binding.imageButton.setOnClickListener {
           binding.mainContainer.transitionToStart()
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.updatePlayerPosition(seekBar?.toPlayerPosition())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                viewModel.shouldUpdateSeekbar = false
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    viewModel.seekTo(seekBar.toPlayerPosition())
                    viewModel.shouldUpdateSeekbar = true
                }
            }
        })

    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onBackPressed() {

        if(binding.mainContainer.progress == 1.0f){
            binding.mainContainer.transitionToStart()
        }else{
            super.onBackPressed()
        }

    }

}