package com.example.lab03.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lab03.ui.model.SongUi
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon


data class SectionUi(val name: String, val songs: List<SongUi>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    songs: List<SongUi>,
    onSongClick: (SongUi) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onGoSearch: () -> Unit
) {

    val rockIds = setOf("song_1","song_2","song_3","song_4")
    val focusIds = setOf("song_5","song_6","song_7","song_8")

    val sections = listOf(
        SectionUi("Rock Classics", songs.filter { it.id in rockIds }),
        SectionUi("Coding Focus", songs.filter { it.id in focusIds }),
        SectionUi("Rap / Hip-Hop", songs.filter { it.id.startsWith("drake_") || it.id.startsWith("travis_") }),
        SectionUi("Reggaetón / Latino", songs.filter { it.id.startsWith("bb_") || it.id.startsWith("anuel_") }),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StreamUI") },
                actions = {
                    IconButton(onClick = onGoSearch) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            sections.forEach { section ->
                Text(section.name, style = MaterialTheme.typography.titleMedium)

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(section.songs) { song ->
                        SongTile(
                            title = song.title,
                            subtitle = song.artist,
                            isFavorite = song.isFavorite,
                            onClick = { onSongClick(song) },
                            onFavoriteClick = { onToggleFavorite(song.id) }
                        )

                    }
                }
            }
        }
    }
}

@Composable
private fun SongTile(
    title: String,
    subtitle: String,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clickable(onClick = onClick)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(34.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "Favorite"
                )
            }
        }

        Text(
            subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
            maxLines = 1
        )

    }
}
