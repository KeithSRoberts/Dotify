package com.example.dotify

class MusicManager(initSongList: List<Song>) {
    var songList: List<Song> = initSongList
    var currentSong: Song? = null

    fun play(song: Song) {
        currentSong = song
    }

    fun updateSongList(newSongList: List<Song>) {
        songList = newSongList
    }
}
