package com.sana.habituniverse.presentation.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sana.habituniverse.presentation.ui.CommonScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HabitPostScreen(
    navController: NavHostController,
    viewModel: HabitPostViewModel
) {
    val habitPostState by viewModel.collectAsState()

    viewModel.collectSideEffect(sideEffect = {
        when (it) {
            HabitPostSideEffect.GoToHome -> {
                navController.popBackStack()
            }
        }
    })

    CommonScreen(
        rightIcon = Icons.Default.Check,
        leftIcon = Icons.Default.ArrowBack,
        onLeftClick = { viewModel.onEvent(HabitPostEvent.BackClick) },
        onRightClick = {
            viewModel.onEvent(
                HabitPostEvent.SaveHabit(habitPostState.item)
            )
        },
    ) {
        HabitPostContent(
            name = habitPostState.item.name,
            initialHour = habitPostState.item.alarmHour,
            initialMinute = habitPostState.item.alarmMinute,
            onNameChange = {
                viewModel.onEvent(HabitPostEvent.NameChange(it))
            },
            onTimeChange = { hour, minute ->
                viewModel.onEvent(HabitPostEvent.TimeChange(hour, minute))
            }
        )
    }
}

@Composable
fun HabitPostContent(
    name: String,
    initialHour: Int,
    initialMinute: Int,
    onNameChange: (String) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            label = { Text(text = "습관 제목을 작성해주세요") },
            singleLine = true,
            onValueChange = {
                onNameChange(it)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showDialog = true }
        ) {
            Text(text = "Show Time Picker")
        }

        if (showDialog) {
            TimePicker(
                initialHour = initialHour,
                initialMinute = initialMinute,
                onConfirm = { hour, minute ->
                    showDialog = false
                    onTimeChange.invoke(hour, minute)
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (Int, Int) -> Unit, onDismiss: () -> Unit
) {

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute
    )

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeInput(state = timePickerState)

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = "Dismiss")
                }

                TextButton(
                    onClick = {
                        onConfirm(timePickerState.hour, timePickerState.minute)
                        onDismiss()
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}