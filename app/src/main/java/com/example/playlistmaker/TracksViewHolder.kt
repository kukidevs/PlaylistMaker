package com.example.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.*

class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val trackImage: ImageView = itemView.findViewById(R.id.trackIcon)
    private val trackAuthor: TextView = itemView.findViewById(R.id.trackAuthor)
    private val trackLength: TextView = itemView.findViewById(R.id.trackLength)

    fun bind(model: Track) {
        trackName.text = model.trackName
        trackAuthor.text = model.artistName
        trackLength.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis.toInt()).toString()
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.track_icon_placeholder)
            .transform(
            RoundedCorners(5)
        ).into(trackImage)

    }
}