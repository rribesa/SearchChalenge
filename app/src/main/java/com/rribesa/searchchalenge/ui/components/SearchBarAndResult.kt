package com.rribesa.searchchalenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rribesa.searchchalenge.ui.model.SearchResult

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBarAndResult(
    navController: NavController,
    searchHistory: List<String>,
    searchResult: List<SearchResult>,
    loading: Boolean,
    showEmptyState: Boolean,
    action: (term: String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        SearchBar(searchText, action)
        SearchHistory(searchHistory, action)
        // Search Result
        if (loading) {
            Loading()
        } else if (showEmptyState) {
            EmptyCard()
        } else {
            searchResult.forEach { result ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            navController.navigate("details/${result.title}")
                        }
                ) {
                    ResultCard(result = result)
                }
            }
        }
    }
}
