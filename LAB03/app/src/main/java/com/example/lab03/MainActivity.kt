package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
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
import com.example.lab03.ui.screens.HighlightsScreen
import com.example.lab03.ui.theme.LAB03Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab03.ui.viewmodel.SongsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lab03.ui.navigation.HighlightsDestination





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

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val route = currentDestination?.route ?: ""

    val onHome = route.contains("HomeDestination")
    val onHighlights = route.contains("HighlightsDestination")
    val showBottomBar = onHome || onHighlights


    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = onHome,
                        onClick = {
                            navController.navigate(HomeDestination) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = onHighlights,
                        onClick = {
                            navController.navigate(HighlightsDestination) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Filled.Star, contentDescription = "Highlights") },
                        label = { Text("Highlights") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination,
            modifier = Modifier.padding(padding)
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

            composable<HighlightsDestination> {
                HighlightsScreen(
                    songs = songs,
                    onSongClick = { song ->
                        navController.navigate(
                            PlayerDestination(song.id, song.title, song.artist, song.isFavorite)
                        )
                    },
                    onToggleFavorite = { id -> vm.toggleFavorite(id) }
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
}

