package com.tonmoy.gakk.meow.musicplayer.ui.activity

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tonmoy.gakk.meow.musicplayer.R
import com.tonmoy.gakk.meow.musicplayer.databinding.ActivityMainBinding
import com.tonmoy.gakk.meow.musicplayer.utils.toPlayerPosition
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel:MainActivityViewModel by viewModel()
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
}