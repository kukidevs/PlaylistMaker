package com.example.playlistmaker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap

const val PLAYLISTMAKER_SHARED_PREFS = "practicum_example_preferences"
const val DARK_MODE_KEY = "key_for_dark_mode"
const val SEARCH_HISTORY_KEY = "search_history_key"
var sharedPrefs: SharedPreferences? = null
class App : Application() {
    var darkTheme = false
    override fun onCreate() {
        super.onCreate()
        sharedPrefs = getSharedPreferences(PLAYLISTMAKER_SHARED_PREFS, MODE_PRIVATE)
        val restored = sharedPrefs?.getBoolean(DARK_MODE_KEY, false)
        if (restored != null) {
            darkTheme = restored
        }
    }
    fun switchTheme(darkThemeEnabled: Boolean) {
        val sharedPrefs = getSharedPreferences(PLAYLISTMAKER_SHARED_PREFS, MODE_PRIVATE)
        darkTheme = darkThemeEnabled
        sharedPrefs.edit()
            .putBoolean(DARK_MODE_KEY, darkTheme)
            .apply()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    fun read(sharedPrefs: SharedPreferences?): ArrayList<Track>{
        val json = sharedPrefs?.getString(SEARCH_HISTORY_KEY, "")
        println(json)
        val converted = Gson().fromJson(json, Array<Track>::class.java) ?: arrayOf()
        val toReturn = ArrayList<Track>()
        for(track in converted) toReturn.add(track)
        return toReturn
    }

    fun add(track: Track){
        val tracks = read(sharedPrefs)
        if(track.trackId in tracks.map{ t -> t.trackId }) tracks.remove(track)
        tracks.add(0, track)
        if(tracks.size == 11) tracks.remove(tracks[tracks.size-1])
        sharedPrefs!!.edit()
            .putString(SEARCH_HISTORY_KEY, Gson().toJson(tracks))
            .apply()
    }

    fun clear(sharedPrefs: SharedPreferences, key:String){
        sharedPrefs.edit()
            .remove(key)
            .apply()
    }
}