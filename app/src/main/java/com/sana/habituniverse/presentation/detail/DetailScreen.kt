package com.sana.habituniverse.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen() {
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
                            modifier = Modifier.size(50.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailPreview() {
    DetailScreen()
}