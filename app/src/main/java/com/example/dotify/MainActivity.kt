package com.example.dotify

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    private val min = 0;
    private val max = 1000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide title bar within activity
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {}
        setContentView(R.layout.activity_main)

        // Set initial random play count
        var randomPlays: Int = Random().nextInt(max - min + 1) + min;
        val playNums: TextView = findViewById(R.id.textView3)
        playNums.text = "$randomPlays plays";

        val nextButton: ImageView = findViewById(R.id.imageView4);
        nextButton.setOnClickListener() {
            Toast.makeText(applicationContext,"Skipping to next track", Toast.LENGTH_SHORT).show();
        }

        val prevButton: ImageView = findViewById(R.id.imageView2);
        prevButton.setOnClickListener() {
            Toast.makeText(applicationContext,"Skipping to previous track", Toast.LENGTH_SHORT).show();
        }

        val playButton: ImageView = findViewById(R.id.imageView3);
        playButton.setOnClickListener() {
            randomPlays++;
            playNums.text = "$randomPlays plays";
        }
    }
}
