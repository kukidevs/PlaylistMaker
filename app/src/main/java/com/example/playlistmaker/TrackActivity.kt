package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class TrackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)
        val track: Track = Gson().fromJson(intent.getStringExtra("track"), Track::class.java)
        val backButton = findViewById<ImageButton>(R.id.back_button)
        val trackName = findViewById<TextView>(R.id.name)
        val trackAuthor = findViewById<TextView>(R.id.artist)
        val trackDuration = findViewById<TextView>(R.id.duration_placeholder)
        val trackIcon = findViewById<ImageView>(R.id.cover)
        val trackCountry = findViewById<TextView>(R.id.country_placeholder)
        val trackAlbum = findViewById<TextView>(R.id.album_placeholder)
        val trackGenre = findViewById<TextView>(R.id.genre_placeholder)
        val trackYear = findViewById<TextView>(R.id.year_placeholder)
        backButton.setOnClickListener{
            finish()
        }
        Glide.with(applicationContext)
            .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
            .placeholder(R.drawable.track_icon_placeholder)
            .transform(RoundedCorners(16)).into(trackIcon)
        trackName.text = track.trackName
        trackAuthor.text = track.artistName
        trackDuration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toInt()).toString()
        trackCountry.text = track.country
        trackAlbum.text = track.collectionName
        trackGenre.text = track.primaryGenreName
        trackYear.text = track.releaseDate.split('-')[0]
    }
}