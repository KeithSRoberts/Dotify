package com.example.dotify

import com.google.gson.Gson
import com.example.dotify.MockSongData.MOCK_DATA.MOCK_SONG_BATCH
import com.example.dotify.MockSongData.MOCK_DATA.MOCK_SONG

class ApiManager {
    fun fetchSongs(): List<Song> {
        val gson = Gson()
        val songBatch: SongBatch = gson.fromJson(MOCK_SONG_BATCH, SongBatch::class.java)
        return songBatch.songs
    }
}