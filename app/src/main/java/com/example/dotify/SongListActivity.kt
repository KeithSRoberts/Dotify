package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        // Normal click listener functionality for selecting a new song in the list
        songListAdapter.onSongClickListener = { song ->
            var title = song.title
            var artist = song.artist
            var album = song.largeImageID
            currSong.text = "$title - $artist"
            intent.putExtra(TITLE_KEY, title)
            intent.putExtra(ARTIST_KEY, artist)
            intent.putExtra(ALBUM_KEY, album)
        }

        // Long click listener functionality for deleting a song in the list
        songListAdapter.onSongLongClickListener = { song ->
            val newSongList = allSongs.toMutableList()
            val songListIterator = newSongList.iterator()
            for (currSong in songListIterator) {
                if (currSong.id == song.id) {
                    val title = currSong.title
                    songListIterator.remove()
                    Toast.makeText(this, "Removed song: $title", Toast.LENGTH_SHORT).show()
                }
            }
            songListAdapter.setNewSongs(newSongList.toList())
        }

        // Click listener for shuffling songs in the song list via the shuffleButton
        shuffleButton.setOnClickListener {
            val newSongList = allSongs.shuffled()
            songListAdapter.setNewSongs(newSongList)
        }

        // Click listener to send to the main activity view when clicking on the mini player
        currSong.setOnClickListener {
            startActivity(intent)
        }

        songList.adapter = songListAdapter
    }
}
