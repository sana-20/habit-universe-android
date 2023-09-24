package com.sana.habituniverse.presentation.post

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sana.habituniverse.common.utils.log
import com.sana.habituniverse.presentation.ui.CommonInputField
import com.sana.habituniverse.presentation.ui.CommonScreen

@Composable
fun HabitPostScreen(navController: NavHostController, id: String?) {
    log("HabitPostScreen: $id"  )
    CommonScreen(
        rightIcon = Icons.Default.Check,
        leftIcon = Icons.Default.ArrowBack,
        onLeftClick = { navController.popBackStack() },
        onRightClick = { navController.popBackStack() },
    ) {
        HabitPostContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitPostContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        CommonInputField(
            value = "",
            label = "습관 제목을 작성해주세요",
            onValueChange = {
                log("onValueChange: $it")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        val state = rememberTimePickerState(
            initialHour = 21,
            initialMinute = 0,
            is24Hour = false
        )
        TimeInput(
            state = state,
        )
    }
}