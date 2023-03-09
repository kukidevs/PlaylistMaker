package com.example.playlistmaker


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class TracksAdapter(
) : RecyclerView.Adapter<TracksViewHolder> () {
    var tracks = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val t = tracks[position]
        holder.bind(t)
        val activity = holder.itemView
        activity.setOnClickListener {
            val app = App()
            app.add(tracks[position])
            //intent.putExtra(listBicycle)[position]
            activity.context.startActivity(Intent(activity.context, TrackActivity::class.java)
                .putExtra("track", Gson().toJson(t)))
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}