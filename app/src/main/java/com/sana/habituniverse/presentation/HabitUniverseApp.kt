package com.sana.habituniverse.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sana.habituniverse.presentation.check.HabitCheckScreen
import com.sana.habituniverse.presentation.detail.HabitDetailScreen
import com.sana.habituniverse.presentation.home.HabitHomeScreen
import com.sana.habituniverse.presentation.post.HabitPostScreen

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
                IconButton(
                    onClick = { navController.navigate(HabitUniverseScreen.Post.route) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Add")
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
                HabitHomeScreen(navController = navController)
            }
            composable(HabitUniverseScreen.Post.route) {
                HabitPostScreen(navController = navController)
            }
            composable(
                HabitUniverseScreen.Detail.route + "/{title}",
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) {
                HabitDetailScreen(navController = navController, title = it.arguments?.getString("title") ?: "")
            }
            composable(HabitUniverseScreen.Check.route) {
                HabitCheckScreen(navController = navController)
            }
        }
    }
}