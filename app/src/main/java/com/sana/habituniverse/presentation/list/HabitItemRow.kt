package com.sana.habituniverse.presentation.list

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
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

data class HabitItem(val title: String, val progressDays: Int)

@Composable
fun HabitItemRow(habit: HabitItem) {
    var expanded by remember { mutableStateOf(false) }
    val contextForToast = LocalContext.current.applicationContext

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = habit.title, style = LocalTextStyle.current.copy(fontSize = 20.sp))
            Text(text = "Progress Days: ${habit.progressDays}")
        }

        Spacer(modifier = Modifier.weight(1f))


        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    expanded = true
                }
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "MoreVert")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // menu items
                DropdownMenuItem(
                    text = {
                        Text("수정")
                    },
                    onClick = {
                        Toast.makeText(contextForToast, "수정", Toast.LENGTH_SHORT).show()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("삭제")
                    },
                    onClick = {
                        Toast.makeText(contextForToast, "삭제", Toast.LENGTH_SHORT).show()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("보관")
                    },
                    onClick = {
                        Toast.makeText(contextForToast, "보관", Toast.LENGTH_SHORT).show()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.MailOutline,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}



@Preview
@Composable
fun HabitItemRowPreview() {
    HabitItemRow(habit = HabitItem("Exercise", 5))
}