package com.example.playlistmaker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = getSharedPreferences(PLAYLISTMAKER_SHARED_PREFS, MODE_PRIVATE)
        val restored = sharedPrefs.getBoolean(DARK_MODE_KEY, false)
        if(restored){
                (applicationContext as App).switchTheme(true)
        }else{
                (applicationContext as App).switchTheme(false)
            }

        setContentView(R.layout.activity_main)
        val version = findViewById<TextView>(R.id.version)
        version.setOnClickListener{
            version.setTextColor(getColor(R.color.white))
        }
        val buttonLib = findViewById<Button>(R.id.library_button_main)
        buttonLib.setOnClickListener {
            val displayIntent = Intent(this, LibraryActivity::class.java)
            startActivity(displayIntent)
        }
        val buttonSearch =findViewById<Button>(R.id.search_button_main)
        val buttonClickListener: View.OnClickListener = object: View.OnClickListener{
            override fun onClick(p0: View?) {
                val displayIntent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(displayIntent)
            }
        }
        buttonSearch.setOnClickListener(buttonClickListener)
        val buttonSettings = findViewById<Button>(R.id.settings_button_main)
        buttonSettings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }

    }
}