package com.example.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_detail.*
import kotlin.random.Random


class NowPlayingFragment : Fragment() {

    private val min = 0;
    private val max = 10000;
    private var randomNum = 0;
    private var currSong: Song? = null

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG_PLAYS = "SONG_PLAYS"
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Take in song as fragment arg
        arguments?.let {args ->
            val song = args.getParcelable<Song>(SONG_KEY)
            if (song != null) {
                this.currSong = song
            }
        }

        // Check if there is a saved song play number, if not make a random number for plays
        if (savedInstanceState == null) {
            randomNum = Random.nextInt(min, max)
        } else {
            //Otherwise use the number of plays from our saved instance
            with(savedInstanceState) {
                randomNum = getInt(SONG_PLAYS, Random.nextInt(min, max))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Show music player screen from hw1
        return layoutInflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playNums.text = randomNum.toString()
        playButton.setOnClickListener {
            randomNum += 1
            playNums.text = randomNum.toString()
        }
        nextButton.setOnClickListener {
            makeToast("Skipping to next track")
        }
        prevButton.setOnClickListener {
            makeToast("Skipping to previous track")
        }

        arguments?.let {args ->
            val song = args.getParcelable<Song>(SONG_KEY)
            if (song != null) {
                this.updateSong(song)
            }
        }
    }

    // Overriding so we an save our number of song plays between states
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(SONG_PLAYS, randomNum)
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun updateSong(song: Song) {
        this.currSong = song;
        songArtist.text = song.artist
        songTitle.text = song.title
        albumCoverImage.setImageResource(song.largeImageID)
    }
}
