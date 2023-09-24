package com.sana.habituniverse.presentation

import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sana.habituniverse.common.utils.log
import com.sana.habituniverse.presentation.check.HabitCheckScreen
import com.sana.habituniverse.presentation.detail.HabitDetailScreen
import com.sana.habituniverse.presentation.home.HabitHomeScreen
import com.sana.habituniverse.presentation.post.HabitPostScreen
import com.sana.habituniverse.presentation.post.HabitPostViewModel
import org.orbitmvi.orbit.compose.collectAsState

enum class HabitUniverseScreen(val route: String) {
    Home("home"),
    Post("post"),
    Detail("detail"),
    Check("check"),
}

@Composable
fun HabitUniverseApp(
    navController: NavHostController = rememberNavController()
) {
    val currentRoute = navController
        .currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)

    Scaffold(
        floatingActionButton = {
            if (currentRoute.value?.destination?.route == HabitUniverseScreen.Home.route) {
                FloatingActionButton(
                    onClick = { navController.navigate(HabitUniverseScreen.Post.route) },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HabitUniverseScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HabitUniverseScreen.Home.route) {
                HabitHomeScreen(
                    navController = navController,
                    viewModel = hiltViewModel()
                )
            }
            composable(
                HabitUniverseScreen.Post.route + "?id={id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                val viewModel = hiltViewModel<HabitPostViewModel>()
                viewModel.load(it.arguments?.getString("id") ?: "")

                HabitPostScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(
                HabitUniverseScreen.Detail.route + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                HabitDetailScreen(
                    navController = navController,
                    id = it.arguments?.getString("id") ?: ""
                )
            }
            composable(HabitUniverseScreen.Check.route) {
                HabitCheckScreen(navController = navController)
            }
        }
    }
}