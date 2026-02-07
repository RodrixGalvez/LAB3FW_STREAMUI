package com.example.lab03.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lab03.ui.model.ALL_SONGS
import com.example.lab03.ui.model.SongUi

class SongsViewModel : ViewModel() {

    var songs by mutableStateOf<List<SongUi>>(ALL_SONGS)
        private set

    fun toggleFavorite(id: String) {
        songs = songs.map { s ->
            if (s.id == id) s.copy(isFavorite = !s.isFavorite) else s
        }
    }
}
