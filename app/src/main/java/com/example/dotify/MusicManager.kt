package com.example.dotify

import com.ericchee.songdataprovider.SongDataProvider
import com.ericchee.songdataprovider.Song

class MusicManager {
    lateinit var songList: List<Song>
    var currentSong: Song? = null

    init {
        // Only call of getAllSongs()
        songList = SongDataProvider.getAllSongs()
    }

    fun play(song: Song) {
        currentSong = song
    }

    fun updateSongList(newSongList: List<Song>) {
        songList = newSongList
    }
}
