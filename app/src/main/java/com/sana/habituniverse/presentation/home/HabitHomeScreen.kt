package com.sana.habituniverse.presentation.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sana.habituniverse.presentation.HabitUniverseScreen
import com.sana.habituniverse.presentation.ui.CommonScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

enum class Filter {
    All, InProgress, Completed
}

enum class Sort {

}

@Composable
fun HabitHomeScreen(navController: NavHostController, viewModel: HabitHomeViewModel) {
    var showSheet by remember { mutableStateOf(false) }
    val habitHomeState by viewModel.collectAsState()
    viewModel.collectSideEffect(sideEffect = {
        when (it) {
            is HabitHomeSideEffect.GoToDetail -> {
                navController.navigate(HabitUniverseScreen.Detail.route + "/$it")
            }
            is HabitHomeSideEffect.GoToPost -> {
                navController.navigate(HabitUniverseScreen.Post.route + "?id=$it")
            }
        }
    })

    CommonScreen(
        rightIcon = Icons.Default.List,
        onRightClick = {
            showSheet = true
        }
    ) {
        if (showSheet) {
            BottomSheet() {
                showSheet = false
            }
        }
        when (val state = habitHomeState) {
            is HabitHomeState.Loaded -> {
                HabitHomeContent(
                    onItemClick = {
                        navController.navigate(HabitUniverseScreen.Detail.route + "/$it")
                    },
                    habits = state.habits,
                    onArchiveClick = {
                        viewModel.onEvent(HabitHomeEvent.ArchiveHabit(it))
                    },
                    onEditClick = {
                        viewModel.onEvent(HabitHomeEvent.EditHabit(it))
                    },
                    onDeleteClick = {
                        viewModel.onEvent(HabitHomeEvent.DeleteHabit(it))
                    }
                )
            }

            HabitHomeState.Loading -> {

            }
        }
    }
}

@Composable
fun HabitHomeContent(
    habits: List<HabitHomeItem>,
    onItemClick: (String) -> Unit,
    onArchiveClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(habits) { habit ->
                HabitItemRow(
                    habit = habit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onItemClick(habit.id) },
                    onArchiveClick = onArchiveClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}

@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        FilterList()
    }
}

@Composable
fun FilterList() {
    val itemsList = listOf("전체", "진행중", "완료")

    val contextForToast = LocalContext.current.applicationContext

    var selectedItem by remember {
        mutableStateOf(itemsList[0])
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(itemsList) { item ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                selected = (item == selectedItem),
                onClick = {
                    selectedItem = item
                    Toast.makeText(contextForToast, selectedItem, Toast.LENGTH_SHORT).show()
                },
                label = {
                    Text(text = item)
                }
            )
        }
    }
}
