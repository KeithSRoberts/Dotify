package com.example.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment : Fragment() {

    private lateinit var songListAdapter: SongListAdapter
    private lateinit var songList : MutableList<Song>
    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val SONG_LIST_KEY = "SONG_LIST_KEY"
        const val SAVED_SONG_LIST = "SAVED_SONG_LIST"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            arguments?.let { args ->
                songList = args.getParcelableArrayList<Song>(SONG_LIST_KEY) as MutableList<Song>
            }
        } else {
            with(savedInstanceState) {
                songList = getParcelableArrayList<Song>(SAVED_SONG_LIST) as MutableList<Song>
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songListAdapter = SongListAdapter(songList)
        rvSongList.adapter = songListAdapter

        songListAdapter.onSongClickListener = { song ->
            onSongClickListener?.onSongClicked(song)
        }
    }

    // Override so that we can save our song list between states of our instance
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SAVED_SONG_LIST, songList as ArrayList<Song>)
    }

    fun shuffleList() {
        songList.shuffle()
        songListAdapter.setNewSongs(songList.toMutableList());
    }
}