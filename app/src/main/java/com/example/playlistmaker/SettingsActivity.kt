package com.example.playlistmaker

import android.content.Intent
import android.content.Intent.*
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonBack = findViewById<Button>(R.id.settings_arrow_back)
        val nightModeSwitch = findViewById<SwitchCompat>(R.id.nightModeSwitch)
        when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {nightModeSwitch.isChecked = true}
            Configuration.UI_MODE_NIGHT_NO -> {nightModeSwitch.isChecked = false}
        }
        buttonBack.setOnClickListener {
            finish()
        }
        nightModeSwitch.setOnCheckedChangeListener { compoundB, isOn ->
            (applicationContext as App).switchTheme(isOn)
//            if (isChecked){
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else{
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//        }
        }
        val shareApp = findViewById<LinearLayout>(R.id.share_app_line)
        shareApp.setOnClickListener{
            val sendIntent: Intent = Intent(ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_link))
                type = "text/plain" }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        val messageSupport = findViewById<LinearLayout>(R.id.message_support_line)
        messageSupport.setOnClickListener{
            val sendIntent: Intent = Intent(ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(EXTRA_EMAIL, arrayOf("kukidevs@gmail.com"))
                putExtra(EXTRA_SUBJECT, getString(R.string.support_mail_title))
                putExtra(EXTRA_TEXT, R.string.support_mail_text)
                }
                startActivity(sendIntent)
        }
        val userAgreement = findViewById<LinearLayout>(R.id.user_agreement_line)
        userAgreement.setOnClickListener{
            val browserIntent = Intent(ACTION_VIEW, Uri.parse("https://yandex.ru/legal/practicum_offer/"))
            startActivity(browserIntent)
        }

    }
}