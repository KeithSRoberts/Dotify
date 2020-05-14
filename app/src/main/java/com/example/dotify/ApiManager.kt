package com.example.dotify

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.example.dotify.MockSongData.MOCK_DATA.MOCK_SONG_BATCH
import com.example.dotify.MockSongData.MOCK_DATA.MOCK_SONG

class ApiManager(context: Context) {
    val requestQueue = Volley.newRequestQueue(context);

    fun fetchSongs(onSuccess: (List<Song>) -> Unit, onError: (String) -> Unit){
        val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"
        val request = StringRequest(Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val songBatch: SongBatch = gson.fromJson(response, SongBatch::class.java)
                onSuccess(songBatch.songs)
            },
            {
                onError.invoke(it.toString())
            })
        requestQueue.add(request)
    }
}