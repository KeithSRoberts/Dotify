package com.example.dotify

data class SongBatch (
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)