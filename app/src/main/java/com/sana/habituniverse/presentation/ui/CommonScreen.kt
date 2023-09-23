package com.sana.habituniverse.presentation.ui

import android.icu.text.CaseMap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CommonScreen(
    title: String? = null,
    rightIcon: ImageVector? = null,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    leftIcon: ImageVector? = null,
    content: @Composable () -> Unit = {},
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leftIcon != null) {
                IconButton(
                    onClick = onLeftClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(imageVector = leftIcon, contentDescription = "Back")
                }
            }
            if (title != null) {
                Text(text = title, modifier = Modifier.weight(1f))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            if (rightIcon != null) {
                IconButton(
                    onClick = onRightClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(imageVector = rightIcon, contentDescription = "Check")
                }
            }
        }
        content()
    }
}
