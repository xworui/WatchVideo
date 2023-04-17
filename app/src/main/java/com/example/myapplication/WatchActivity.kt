package com.example.myapplication

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class WatchActivity : AppCompatActivity() {


    lateinit var binding: ActivityWatchBinding
    var isFullScreen = false
    var isLock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val simpleExoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()

        val btFullScreen = findViewById<ImageView>(R.id.btFullScreen)
        val btLockScreen = findViewById<ImageView>(R.id.exo_lock)

        btFullScreen.setOnClickListener {
            if (!isFullScreen) {
                btFullScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen_exit))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
            else {
                btFullScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            isFullScreen = !isFullScreen
        }

        btLockScreen.setOnClickListener {
            if (!isLock) {
                btLockScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_lock))
            }
            else {
                btLockScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_lock_open))
            }
            isLock = !isLock
            lockScreen(isLock)
        }

        binding.player.player = simpleExoPlayer
        binding.player.keepScreenOn = true
        simpleExoPlayer.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    binding.progressbar.visibility = View.VISIBLE
                }
                else if(playbackState == Player.STATE_READY) {
                    binding.progressbar.visibility = View.GONE
                }
            }
        })
        val videoSource = Uri.parse("https://github.com/MaxS2021/VideoArch/raw/7f83745d7104f6c7d5418401b5afb08a34a081a2/big-buck-bunny-360p.mp4")
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    private fun lockScreen(lock: Boolean) {
        val sec_mid = findViewById<LinearLayout>(R.id.sec_controlvid1)
        val sec_bot = findViewById<LinearLayout>(R.id.sec_controlvid2)
        if (lock) {
            sec_mid.visibility = View.INVISIBLE
            sec_bot.visibility = View.INVISIBLE
        }
        else {
            sec_mid.visibility = View.VISIBLE
            sec_bot.visibility = View.VISIBLE
        }
    }
}