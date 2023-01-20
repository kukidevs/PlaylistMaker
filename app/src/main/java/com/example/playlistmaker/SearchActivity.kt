package com.example.playlistmaker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi

class SearchActivity : AppCompatActivity() {
    var inputTextIn = ""
    companion object {
        private const val QUERY_LINE = "QUERY_LINE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val backIcon = findViewById<ImageView>(R.id.iconBack)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        clearButton.setOnClickListener {
            inputEditText.setText("")
        }
        backIcon.setOnClickListener{
            val displayIntent = Intent(this, MainActivity::class.java)
            startActivity(displayIntent)
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("saved")
        outState.putString(QUERY_LINE, inputTextIn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        println("restored")
        super.onRestoreInstanceState(savedInstanceState)
        inputTextIn = savedInstanceState.getString(QUERY_LINE, "")
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


}