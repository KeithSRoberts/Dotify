package com.example.dotify

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*


class MainActivity : AppCompatActivity() {

    private val min = 0;
    private val max = 1000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set initial random play count
        var randomPlays: Int = Random().nextInt(max - min + 1) + min;
        val playNums: TextView = findViewById(R.id.textView3)
        playNums.text = "$randomPlays plays";

        // Change user functionality
        val changeUserButton: Button = findViewById(R.id.button);
        val usernameText: TextView = findViewById(R.id.username);
        val editText: EditText = findViewById(R.id.editText);
        editText.visibility = View.GONE;
        changeUserButton.setOnClickListener() {
            if (changeUserButton.text == "Change User") {
                changeUserButton.text = "Apply";
                editText.setText("");
                usernameText.visibility = View.GONE;
                editText.visibility = View.VISIBLE;
            } else if (changeUserButton.text == "Apply" && editText.text.toString() != "") {
                changeUserButton.text = "Change User";
                usernameText.text = editText.text;
                editText.visibility = View.GONE;
                usernameText.visibility = View.VISIBLE;
            }
        }

        // Next button functionality
        val nextButton: ImageView = findViewById(R.id.imageView4);
        nextButton.setOnClickListener() {
            Toast.makeText(applicationContext,"Skipping to next track", Toast.LENGTH_SHORT).show();
        }

        // Previous button functionality
        val prevButton: ImageView = findViewById(R.id.imageView2);
        prevButton.setOnClickListener() {
            Toast.makeText(applicationContext,"Skipping to previous track", Toast.LENGTH_SHORT).show();
        }

        // Play button functionality
        val playButton: ImageView = findViewById(R.id.imageView3);
        playButton.setOnClickListener() {
            randomPlays++;
            playNums.text = "$randomPlays plays";
        }

        // Cover image long press functionality
        val parentCL: ConstraintLayout = findViewById(R.id.constraint);
        val coverImage: ImageView = findViewById(R.id.imageView);
        coverImage.setOnLongClickListener() {
            val rnd = Random()
            val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            for (i in 0 until parentCL.childCount) {
                if (parentCL.getChildAt(i) is TextView) {
                    val tv = parentCL.getChildAt(i) as TextView
                    tv.setTextColor(color)
                }
            }
            true
        }
    }
}
