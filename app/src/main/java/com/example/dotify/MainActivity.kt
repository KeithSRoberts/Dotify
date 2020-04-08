package com.example.dotify

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    private val min = 0;
    private val max = 1000;
    private val randomPlays: Int = Random().nextInt(max - min + 1) + min;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playNums: TextView = findViewById(R.id.textView3)
        val text = "$randomPlays plays";
        playNums.text = text;
    }
}
