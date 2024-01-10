package com.rribesa.searchchalenge.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rribesa.searchchalenge.ui.theme.SearchChalengeTheme

@Composable
fun ColumnScope.SearchHistory(
    searchHistory: List<String>,
    action: (term: String) -> Unit
) {
    val animationSpec = tween<Float>()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        itemsIndexed(searchHistory) { index, historyItem ->
            AnimatedVisibility(
                visible = index < searchHistory.size,
                enter = fadeIn(animationSpec),
                exit = fadeOut(animationSpec)
            ) {
                Text(
                    text = historyItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            action(historyItem)
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchHistoryPreview() {
    SearchChalengeTheme {
        Column {
            SearchHistory(searchHistory = listOf("teste1", "teste2") ) {
            }
        }
    }
}