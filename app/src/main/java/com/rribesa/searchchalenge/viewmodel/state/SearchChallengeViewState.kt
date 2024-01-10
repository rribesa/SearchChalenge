package com.rribesa.searchchalenge.viewmodel.state

import com.rribesa.searchchalenge.ui.model.SearchResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class SearchChallengeViewState {
    val isLoading = MutableStateFlow(false)
    val searchHistory: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val searchResult: MutableStateFlow<List<SearchResult>> = MutableStateFlow(emptyList())
    val shouldShowError = MutableStateFlow(false)
    val isEmptyState = MutableStateFlow(searchResult.value.isEmpty())
    val action = MutableSharedFlow<Action>()


    sealed class Action {
        data class NavigateDetails(val item: SearchResult) : Action()
    }
}


