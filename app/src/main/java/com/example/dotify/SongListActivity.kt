package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.MainActivity.Companion.ALBUM_KEY
import com.example.dotify.MainActivity.Companion.TITLE_KEY
import com.example.dotify.MainActivity.Companion.ARTIST_KEY

class SongListActivity : AppCompatActivity() {

    private lateinit var currSong : TextView
    private lateinit var shuffleButton : Button
    private lateinit var songList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        songList = findViewById(R.id.songList)
        currSong = findViewById(R.id.currSong)
        shuffleButton = findViewById(R.id.shuffleButton)

        val intent = Intent(this, MainActivity::class.java)
        val allSongs = SongDataProvider.getAllSongs()
        val songListAdapter = SongListAdapter(allSongs)
        songListAdapter.onSongClickListener = { song ->
            var title = song.title
            var artist = song.artist
            currSong.text = "$title - $artist"
            intent.putExtra(TITLE_KEY, title)
            intent.putExtra(ARTIST_KEY, artist)
            intent.putExtra(ALBUM_KEY, song.largeImageID)
        }

        currSong.setOnClickListener {
            startActivity(intent)
        }

        songList.adapter = songListAdapter
    }
}
