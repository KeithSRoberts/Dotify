package com.example.dotify

import android.widget.Toast

class MusicManager(initSongList: List<Song>) {
    var songList: List<Song> = initSongList
    var currentSong: Song? = null
    var updatedListListener: UpdatedListListener? = null

    fun play(song: Song) {
        currentSong = song
    }

    fun updateSongList(newSongList: List<Song>) {
        songList = newSongList
    }

    interface UpdatedListListener {
        fun onListUpdate(updatedSongList: List<Song>)
        fun onRequestError(err: String)
    }
}
