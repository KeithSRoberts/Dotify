package com.example.dotify

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main_new.*

// Implements OnSongClickListener
class MainSongActivity : AppCompatActivity(), OnSongClickListener {

    private lateinit var musicManager: MusicManager

    override fun onSongClicked(song: Song) {
        val title = song.title
        val artist = song.artist
        tvCurrSong.text = "$artist - $title"
        musicManager.play(song)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)
        val dotifyApp = application as DotifyApp
        musicManager = dotifyApp.musicManager
        musicManager.currentSong?.let {
            val title = it.title
            val artist = it.artist
            tvCurrSong.text = "$artist - $title"
        }

        shuffleButton.setOnClickListener {
            val listFrag = supportFragmentManager.findFragmentById(R.id.frameContainer) as? SongListFragment
            listFrag?.shuffleList()
        }

        playerContainer.setOnClickListener {
            musicManager.currentSong?.apply {
                playerClickCallback(this)
            }
        }

        // Check if we already have a saved instance to determine if we need to setup
        if (savedInstanceState == null) {
            val songListFragment = SongListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frameContainer, songListFragment)
                .commit()
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


    private fun playerClickCallback(songCopy: Song) {
        // We must make a copy of the currentSong since it is a mutable property of the activity
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
