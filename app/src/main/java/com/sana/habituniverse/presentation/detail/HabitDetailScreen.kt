package com.sana.habituniverse.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sana.habituniverse.presentation.HabitUniverseScreen
import com.sana.habituniverse.presentation.ui.CommonScreen

@Composable
fun HabitDetailScreen(navController: NavHostController, title: String) {
    CommonScreen(
        leftIcon = Icons.Default.ArrowBack,
        title = title,
        onLeftClick = { navController.popBackStack() }
    ) {
        HabitDetailContent(
            onStarClick = {
                navController.navigate(HabitUniverseScreen.Check.route)
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun HabitDetailContent(onStarClick: () -> Unit) {
    val rows = 7
    val columns = 3
    val sections = listOf("A", "B", "C", "D", "E", "F", "G")

    LazyColumn() {
        sections.forEach { section ->
            stickyHeader {
                Text(
                    text = section,
                    modifier = Modifier.padding(16.dp),
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = rows
                ) {
                    repeat(rows * columns) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Checkmark",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    onStarClick()
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}