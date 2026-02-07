package com.example.lab03.ui.model

import com.example.lab03.ui.screens.SectionUi

data class SongUi(
    val id: String,
    val title: String,
    val artist: String,
    val isFavorite: Boolean = false
)

val ALL_SONGS: List<SongUi> = listOf(

        SongUi("song_1", "Highway to Hell", "AC/DC"),
        SongUi("song_2", "Bohemian Rhapsody", "Queen"),
        SongUi("song_3", "Stairway to Heaven", "Led Zeppelin"),
        SongUi("song_4", "Sweet Child O' Mine", "Guns N' Roses"),
        SongUi("song_5", "Weightless", "Marconi Union"),
        SongUi("song_6", "Clair de Lune", "Debussy"),
        SongUi("song_7", "Experience", "Ludovico Einaudi"),
        SongUi("song_8", "Time", "Hans Zimmer"),
        SongUi("drake_1", "God's Plan", "Drake"),
        SongUi("drake_2", "Hotline Bling", "Drake"),
        SongUi("drake_3", "One Dance", "Drake"),
        SongUi("travis_1", "HIGHEST IN THE ROOM", "Travis Scott"),
        SongUi("travis_2", "SICKO MODE", "Travis Scott"),
        SongUi("travis_3", "BUTTERFLY EFFECT", "Travis Scott"),
        SongUi("bb_1", "Tití Me Preguntó", "Bad Bunny"),
        SongUi("bb_2", "Me Porto Bonito", "Bad Bunny"),
        SongUi("bb_3", "Yonaguni", "Bad Bunny"),
        SongUi("anuel_1", "Secreto", "Anuel AA"),
        SongUi("anuel_2", "Hasta Que Dios Diga", "Anuel AA"),
        SongUi("anuel_3", "La Jeepeta - Remix", "Anuel AA"),
)