package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SongListAdapter(private var allSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener: ((song: Song) -> Unit)? = null
    var onSongLongClickListener: ((song: Song) -> Unit)? = null

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songTitle by lazy {itemView.findViewById<TextView>(R.id.songTitle)}
        private val songArtist by lazy {itemView.findViewById<TextView>(R.id.songArtist)}
        private val albumCoverImage by lazy {itemView.findViewById<ImageView>(R.id.albumCoverImage)}

        fun bind(song: Song) {
            songTitle.text = song.title
            songArtist.text = song.artist
            Picasso.get().load(song.smallImageURL).noFade().into(albumCoverImage)

            itemView.setOnLongClickListener {
                onSongLongClickListener?.invoke(song)
                true
            }

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.song, parent, false)
        return SongViewHolder(item)
    }

    override fun getItemCount(): Int = allSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songName = allSongs[position]
        holder.bind(songName)
    }

    fun setNewSongs(newSongList: List<Song>) {
        val callback = DiffCallback(allSongs, newSongList)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        allSongs = newSongList
        notifyDataSetChanged()
    }
}