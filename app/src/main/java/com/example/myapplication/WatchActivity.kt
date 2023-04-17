package com.example.myapplication

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.view.View
import com.example.myapplication.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class WatchActivity : AppCompatActivity() {

    lateinit var bind: ActivityWatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val simpleExoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()

        bind.player.player = simpleExoPlayer
        bind.player.keepScreenOn = true
        simpleExoPlayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    bind.progressbar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY){
                    bind.progressbar.visibility = View.GONE
                }
            }
        })
        val videoSource = Uri.parse("https:/www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }
}