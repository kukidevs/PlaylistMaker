package com.example.playlistmaker

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    var inputTextIn = ""
    val baseUrl = "https://itunes.apple.com"


    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val iTunesService = retrofit.create(iTunesAPI::class.java)
    companion object {
        private const val QUERY_LINE = "QUERY_LINE"
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backIcon = findViewById<ImageView>(R.id.iconBack)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val errorImage = findViewById<ImageView>(R.id.errorImage)
        val errorText = findViewById<TextView>(R.id.errorText)
        val errorButton = findViewById<MaterialButton>(R.id.errorButton)
        val clearHistory = findViewById<MaterialButton>(R.id.clearButton)
        val previouslySearched = findViewById<TextView>(R.id.previouslySearched)
        val tracksArray = ArrayList<Track>()
        fun changeVisibility(v: Int){
            previouslySearched.visibility = v
            recyclerView.visibility = v
            clearHistory.visibility = v
        }
            //arrayListOf(
//                                                             Track("Smells Like Teen Spirit", "Nirvana", "5:01", "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"),
//                                                             Track("Billie Jean", "Michael Jackson", "4:35", "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"),
//                                                             Track("Stayin' Alive", "Bee Gees", "4:10", "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"),
//                                                             Track("Whole Gotta Love", "Led Zeppelin", "5:33", "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"),
//                                                             Track("Sweet Child O'Mine", "Guns N' Roses", "5:01", "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"))
        val tracksAdapter = TracksAdapter()
        fun makeSearch(){
            changeVisibility(View.GONE)
            tracksArray.clear()
            tracksAdapter.notifyDataSetChanged()
            iTunesService.search(inputEditText.text.toString()).enqueue(object :
                Callback<iTunesResponse> {
                override fun onResponse(call: Call<iTunesResponse>,
                                        response: Response<iTunesResponse>
                ) {
                    if (response.code() == 200) {
                        if(response.body()?.results?.isNotEmpty() == true) {
                            recyclerView.visibility = View.VISIBLE
                            tracksArray.clear()
                            tracksArray.addAll(response.body()!!.results)
                            tracksAdapter.notifyDataSetChanged()
                        }
                        if(response.body()!!.results.isEmpty()){
                            errorImage.visibility = View.VISIBLE
                            errorText.visibility = View.VISIBLE
                            errorImage.setImageResource(R.drawable.nothing_found)
                            errorText.setText(R.string.nothing_found)


                        }
                    }
                }

                override fun onFailure(call: Call<iTunesResponse>, t: Throwable) {
                    errorImage.visibility = View.VISIBLE
                    errorText.visibility = View.VISIBLE
                    errorButton.visibility = View.VISIBLE
                    errorButton.text = getString(R.string.update_button)
                    errorImage.setImageResource(R.drawable.no_internet)
                    errorText.setText(R.string.no_internet)
                }
            })
        }
        recyclerView.adapter = tracksAdapter
        tracksAdapter.tracks = tracksArray
        errorButton.setOnClickListener{
            errorButton.visibility = View.GONE
            errorText.visibility = View.GONE
            errorImage.visibility=View.GONE
            makeSearch()
        }
        clearButton.setOnClickListener {
            inputEditText.setText("")
            tracksArray.clear()
            tracksAdapter.notifyDataSetChanged()
            inputEditText.hideKeyboard()
        }
        inputEditText.setOnFocusChangeListener { view, hasFocus ->
                val t = App()
                val history = t.read(getSharedPreferences(PLAYLISTMAKER_SHARED_PREFS, MODE_PRIVATE))

                if (history.isNotEmpty()) {
                    changeVisibility(View.VISIBLE)
                    tracksArray.clear()
                    tracksArray.addAll(history)
                    tracksAdapter.notifyDataSetChanged()
                    clearHistory.setOnClickListener {
                        t.clear(
                            getSharedPreferences(PLAYLISTMAKER_SHARED_PREFS, MODE_PRIVATE),
                            SEARCH_HISTORY_KEY
                        )
                        tracksArray.clear()
                        tracksAdapter.notifyDataSetChanged()
                        changeVisibility(View.GONE)
                    }

                }
        }
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(inputEditText.text.isNotEmpty()){
                    makeSearch()
                }

            }
            false
        }

        backIcon.setOnClickListener{
            finish()
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                errorButton.visibility = View.GONE
                errorText.visibility = View.GONE
                errorImage.visibility=View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_LINE, inputTextIn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
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