package com.example.dotify

import android.app.Application
import com.example.dotify.MusicManager

class DotifyApp : Application() {
    lateinit var musicManager: MusicManager
    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()
        apiManager = ApiManager()
        musicManager = MusicManager(apiManager.fetchSongs())
    }
}