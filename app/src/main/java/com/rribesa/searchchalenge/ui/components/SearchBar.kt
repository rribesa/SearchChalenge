package com.rribesa.searchchalenge.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rribesa.searchchalenge.ui.theme.SearchChalengeTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBar(
    searchText: String,
    action: (term: String) -> Unit
) {
    var newText = searchText
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = newText,
            onValueChange = {
                newText = it
                action(it)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            label = { Text(text = "Search") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    action(newText)
                }
            )
        )

        IconButton(
            onClick = {
                newText = ""
                action(searchText)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Icon"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchChalengeTheme {
        SearchBar(searchText = "search") {
        }
    }
}