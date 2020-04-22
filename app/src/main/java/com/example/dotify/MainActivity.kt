package com.example.dotify

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.w3c.dom.Text
import java.util.*


class MainActivity : AppCompatActivity() {

    private val min = 0;
    private val max = 1000;
    private lateinit var playNums : TextView;
    private lateinit var nextButton: ImageView;
    private lateinit var prevButton: ImageView;
    private lateinit var playButton: ImageView;
    private lateinit var coverImage: ImageView;
    private lateinit var parentCL: ConstraintLayout;
    private lateinit var changeUserButton: Button;
    private lateinit var usernameText: TextView;
    private lateinit var editUsername: EditText;
    private lateinit var songArtist: TextView;
    private lateinit var songTitle: TextView;

    companion object {
        const val TITLE_KEY = "TITLE_KEY"
        const val ARTIST_KEY = "ARTIST_KEY"
        const val ALBUM_KEY = "ALBUM_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playNums = findViewById(R.id.playNums)
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        playButton = findViewById(R.id.playButton);
        parentCL = findViewById(R.id.constraint);
        coverImage = findViewById(R.id.albumCoverImage);
        changeUserButton = findViewById(R.id.enableEditButton);
        usernameText = findViewById(R.id.username);
        editUsername = findViewById(R.id.editUsername);
        songArtist = findViewById(R.id.songArtist);
        songTitle = findViewById(R.id.songTitle);

        songTitle.text = intent.getStringExtra(TITLE_KEY)
        songArtist.text = intent.getStringExtra(ARTIST_KEY)
        val initialAlbumKey = intent.getIntExtra(ALBUM_KEY, -1)
        coverImage.setImageResource(initialAlbumKey);


        // Set initial random play count
        var randomPlays: Int = Random().nextInt(max - min + 1) + min;
        playNums.text = "$randomPlays plays";

        // Change user functionality
       initChangeUser();

        // Next button functionality
        nextButton.setOnClickListener() {
            makeToast("Skipping to next track")
        }

        // Previous button functionality
        prevButton.setOnClickListener() {
            makeToast("Skipping to previous track")
        }

        // Play button functionality
        playButton.setOnClickListener() {
            randomPlays++;
            playNums.text = "$randomPlays plays";
        }

        // Cover image long press functionality
        initTextColorChange()
    }

    private fun initTextColorChange() {
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

    private fun initChangeUser() {
        editUsername.visibility = View.GONE;
        changeUserButton.setOnClickListener() {
            if (changeUserButton.text == "Change User") {
                changeUserButton.text = "Apply";
                editUsername.setText("");
                usernameText.visibility = View.GONE;
                editUsername.visibility = View.VISIBLE;
            } else if (changeUserButton.text == "Apply" && editUsername.text.toString() != "") {
                changeUserButton.text = "Change User";
                usernameText.text = editUsername.text;
                editUsername.visibility = View.GONE;
                usernameText.visibility = View.VISIBLE;
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show();
    }
}
