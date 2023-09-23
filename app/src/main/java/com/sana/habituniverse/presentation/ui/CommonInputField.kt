package com.sana.habituniverse.presentation.ui

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CommonInputField(value: String, label: String, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(value)) }
    OutlinedTextField(
        value = text,
        label = { Text(text = label) },
        singleLine = true,
        onValueChange = {
            text = it
            onValueChange(it.text)
        }
    )
}

@Preview
@Composable
fun CommonInputFieldPreview() {
    CommonInputField(
        value = "Title",
        label = "Title",
        onValueChange = {  }
    )
}