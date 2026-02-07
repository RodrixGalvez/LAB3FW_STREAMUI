package com.example.lab03.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
object SearchDestination

@Serializable
object HighlightsDestination
@Serializable
data class PlayerDestination(
    val songId: String,
    val title: String,
    val artist: String,
    val isFavorite: Boolean
)




