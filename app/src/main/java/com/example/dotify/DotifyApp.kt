package com.example.dotify

import android.app.Application
import android.widget.Toast
import com.example.dotify.MusicManager

class DotifyApp : Application() {
    lateinit var musicManager: MusicManager
    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()
        apiManager = ApiManager(this)
        musicManager = MusicManager(listOf())
        apiManager.fetchSongs(
            { songBatch ->
                val newSongList = songBatch
                musicManager.updatedListListener?.onListUpdate(newSongList)
            },
            { msg ->
                musicManager.updatedListListener?.onRequestError(msg)
            }
        )
    }


}