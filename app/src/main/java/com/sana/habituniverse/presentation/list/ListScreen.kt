package com.sana.habituniverse.presentation.list

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Filter {
    All, InProgress, Completed
}

enum class Sort {

}

val habitList = listOf(
    HabitItem("Exercise", 5),
    HabitItem("Read", 10),
    HabitItem("Meditate", 3)
)

@Composable
fun ListScreen() {
    var showSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showSheet) {
            BottomSheet() {
                showSheet = false
            }
        }

        HabitListHeader(onFilterIconClick = {
            showSheet = true
        })

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(habitList) { habit ->
                HabitItemRow(
                    habit = habit
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterList() {
    val itemsList = listOf("전체", "진행중", "완료")

    val contextForToast = LocalContext.current.applicationContext

    var selectedItem by remember {
        mutableStateOf(itemsList[0]) // initially, first item is selected
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

@Composable
fun HabitListHeader(onFilterIconClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Habit List",
            style = LocalTextStyle.current.copy(fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onFilterIconClick
        ) {
            Icon(imageVector = Icons.Default.List, contentDescription = "Filter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen()
}
