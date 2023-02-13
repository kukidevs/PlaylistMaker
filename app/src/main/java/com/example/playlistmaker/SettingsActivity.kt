package com.example.playlistmaker

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonBack = findViewById<Button>(R.id.settings_arrow_back)
        buttonBack.setOnClickListener {
            finish()
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