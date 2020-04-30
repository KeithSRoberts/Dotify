package com.example.dotify

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main_new.*

// Implements OnSongClickListener
class MainSongActivity : AppCompatActivity(), OnSongClickListener {

    private var currentSong: Song? = null

    companion object {
        const val SAVED_SONG = "SAVED_SONG"
    }

    override fun onSongClicked(song: Song) {
        val title = song.title
        val artist = song.artist
        tvCurrSong.text = "$artist - $title"
        currentSong = song
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        shuffleButton.setOnClickListener {
            val listFrag = supportFragmentManager.findFragmentById(R.id.frameContainer) as? SongListFragment
            listFrag?.shuffleList()
        }

        playerContainer.setOnClickListener {
            playerClickCallback()
        }

        // Check if we already have a saved instance to determine if we need to setup
        if (savedInstanceState == null) {
            // ONLY call of getAllSongs()
            val allSongs = SongDataProvider.getAllSongs()
            val songBundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.SONG_LIST_KEY, allSongs as ArrayList<Song>)
            }

            val songListFragment = SongListFragment()
            songListFragment.arguments = songBundle
            // Show song list first
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frameContainer, songListFragment)
                .commit()
        } else {
            with(savedInstanceState) {
                currentSong = getParcelable(SAVED_SONG)
                val title = currentSong?.title
                val artist = currentSong?.artist
                tvCurrSong.text = "$artist - $title"
            }
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            playerContainer.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if (!hasBackStack) {
                playerContainer.visibility = View.VISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    // For activity up button functionality
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onSupportNavigateUp()
    }

    // Overriding so that we can save our current song between states
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVED_SONG, currentSong)
    }

    private fun playerClickCallback() {
        // We must make a copy of the currentSong since it is a mutable property of the activity
        val songCopy = currentSong?.copy()
        if (songCopy != null) {
            val playerFrag = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment
            // If we've already instantiated a player fragment just reuse it by updating the song
            if (playerFrag != null) {
                playerFrag.updateSong(songCopy)
            } else {
                // Otherwise, create the player fragment to use and commit it the manager
                val newPlayerFrag = NowPlayingFragment()
                val newPlayerBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.SONG_KEY, songCopy)
                }
                newPlayerFrag.arguments = newPlayerBundle
                playerContainer.visibility = View.INVISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frameContainer, newPlayerFrag)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            }
        }
    }
}
