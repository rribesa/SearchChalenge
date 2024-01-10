package com.rribesa.searchchalenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rribesa.searchchalenge.ui.model.SearchResult
import com.rribesa.searchchalenge.ui.theme.SearchChalengeTheme

@Composable
fun ResultCard(result: SearchResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 72.dp)
                    .fillMaxWidth()
            ) {
                Text(text = result.title, style = MaterialTheme.typography.titleLarge)
                Text(text = result.title, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ResultCardPreview() {
    SearchChalengeTheme {
        ResultCard(result = SearchResult("Test"))
    }
}