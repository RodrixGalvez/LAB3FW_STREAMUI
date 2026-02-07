package com.example.lab03.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.example.lab03.ui.model.SongUi
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    songs: List<SongUi>,
    onSongClick: (SongUi) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onBack: () -> Unit
)
{


    var query by remember { mutableStateOf("") }

    val filtered = remember(query, songs) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) songs
        else songs.filter {
            it.title.lowercase().contains(q) || it.artist.lowercase().contains(q)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar canciones o artistas...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { })
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                filtered.forEachIndexed { idx, song ->
                    SearchRow(
                        title = song.title,
                        subtitle = song.artist,
                        colorIndex = idx,
                        isFavorite = song.isFavorite,
                        onClick = { onSongClick(song) },
                        onFavoriteClick = { onToggleFavorite(song.id) }

                    )
                }
            }
        }
    }
}

@Composable
private fun SearchRow(
    title: String,
    subtitle: String,
    colorIndex: Int,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
        val c1 = MaterialTheme.colorScheme.primary
        val c2 = MaterialTheme.colorScheme.secondary
        val c3 = MaterialTheme.colorScheme.tertiary

        val gradient = when (colorIndex % 3) {
            0 -> Brush.linearGradient(listOf(c1, c2))
            1 -> Brush.linearGradient(listOf(c2, c3))
            else -> Brush.linearGradient(listOf(c3, c1))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(gradient, RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    maxLines = 1
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "Favorite"
                )
            }

        }
    }
