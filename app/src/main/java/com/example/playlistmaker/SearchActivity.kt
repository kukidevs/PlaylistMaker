package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

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
        if (savedInstanceState != null) inputTextIn = savedInstanceState.getString(QUERY_LINE).toString()
        inputEditText.setText(inputTextIn)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            val view = this.currentFocus
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view!=null) imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        backIcon.setOnClickListener{
            finish()
        }
        inputEditText.doOnTextChanged { text, _, _, _ ->
            clearButton.visibility = clearButtonVisibility(text)
            inputTextIn = text.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_LINE, inputTextIn)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


}