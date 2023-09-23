package com.sana.habituniverse.presentation.post

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sana.habituniverse.common.utils.log
import com.sana.habituniverse.presentation.ui.CommonHeader
import com.sana.habituniverse.presentation.ui.CommonInputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CommonHeader()

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
}

@Preview(showBackground = true)
@Composable
fun HabitRegistrationScreenPreview() {
    PostScreen()
}
