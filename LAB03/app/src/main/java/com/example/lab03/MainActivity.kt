package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab03.ui.navigation.HomeDestination
import com.example.lab03.ui.navigation.PlayerDestination
import com.example.lab03.ui.navigation.SearchDestination
import com.example.lab03.ui.screens.HomeScreen
import com.example.lab03.ui.screens.PlayerScreen
import com.example.lab03.ui.screens.SearchScreen
import com.example.lab03.ui.theme.LAB03Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.lab03.ui.model.ALL_SONGS
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab03.ui.viewmodel.SongsViewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB03Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}

@Composable
private fun AppNav() {
    val navController = rememberNavController()
    val vm: SongsViewModel = viewModel()
    val songs = vm.songs

    NavHost(
        navController = navController,
        startDestination = HomeDestination
    ) {
        composable<HomeDestination> {
            HomeScreen(
                songs = songs,
                onSongClick = { song ->
                    navController.navigate(
                        PlayerDestination(song.id, song.title, song.artist, song.isFavorite)
                    )
                },
                onToggleFavorite = { id -> vm.toggleFavorite(id) },
                onGoSearch = { navController.navigate(SearchDestination) }
            )
        }

        composable<SearchDestination> {
            SearchScreen(
                songs = songs,
                onSongClick = { song ->
                    navController.navigate(
                        PlayerDestination(song.id, song.title, song.artist, song.isFavorite)
                    )
                },
                onToggleFavorite = { id -> vm.toggleFavorite(id) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<PlayerDestination> { backStackEntry ->
            val route = backStackEntry.toRoute<PlayerDestination>()

            val currentFav =
                songs.firstOrNull { it.id == route.songId }?.isFavorite ?: route.isFavorite

            PlayerScreen(
                songId = route.songId,
                title = route.title,
                artist = route.artist,
                isFavorite = currentFav,
                onToggleFavorite = { vm.toggleFavorite(route.songId) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
