package com.sana.habituniverse.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MailOutline
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun HabitItemRow(
    habit: HabitHomeItem,
    modifier: Modifier,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onArchiveClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.background(color = if (habit.isCompleted) Color.LightGray else Color.DarkGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = habit.name, style = LocalTextStyle.current.copy(fontSize = 20.sp))
            Text(text = "Progress Days: ${habit.progressDays}")
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
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
                DropdownMenuItem(
                    text = {
                        Text("Edit")
                    },
                    onClick = {
                        expanded = false
                        onEditClick(habit.id)
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
                        Text("Delete")
                    },
                    onClick = {
                        expanded = false
                        onDeleteClick(habit.id)
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
                        Text("Archive")
                    },
                    onClick = {
                        expanded = false
                        onArchiveClick(habit.id)
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