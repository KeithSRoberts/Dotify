package com.example.dotify

import android.app.Application
import com.example.dotify.MusicManager

class DotifyApp : Application() {
    lateinit var musicManager: MusicManager

    override fun onCreate() {
        super.onCreate()
        musicManager = MusicManager()
    }
}