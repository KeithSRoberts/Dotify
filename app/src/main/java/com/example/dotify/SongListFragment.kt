package com.example.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dotify.DotifyApp
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment : Fragment(), MusicManager.UpdatedListListener {

    private lateinit var songListAdapter: SongListAdapter
    private lateinit var songList : MutableList<Song>
    private lateinit var musicManager: MusicManager
    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val SONG_LIST_KEY = "SONG_LIST_KEY"
        const val SAVED_SONG_LIST = "SAVED_SONG_LIST"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dotifyApp = context.applicationContext as DotifyApp
        musicManager = dotifyApp.musicManager
        musicManager.updatedListListener = this
        songList = musicManager.songList.toMutableList()

        if (context is OnSongClickListener) {
            onSongClickListener = context
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

    override fun onListUpdate(updatedSongList: List<Song>) {
        songList = updatedSongList.toMutableList()
        songListAdapter.setNewSongs(songList)
        musicManager.updateSongList(songList)
    }

    override fun onRequestError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    fun shuffleList() {
        songList.shuffle()
        songListAdapter.setNewSongs(songList)
        musicManager.updateSongList(songList)
        rvSongList.scrollToPosition(0)
    }
}