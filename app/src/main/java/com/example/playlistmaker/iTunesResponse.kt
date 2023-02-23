package com.example.playlistmaker

data class iTunesResponse(val resultCount: String,
                           val results: List<Track>)