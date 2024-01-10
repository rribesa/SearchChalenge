package com.rribesa.searchchalenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rribesa.logs.Logger
import com.rribesa.logs.SearchChalengeLog
import com.rribesa.search.domain.usecase.FilterHistoryUseCase
import com.rribesa.search.domain.usecase.GetSearchResultsUseCase
import com.rribesa.searchchalenge.ui.model.SearchResult
import com.rribesa.searchchalenge.viewmodel.action.SearchChallengeViewAction
import com.rribesa.searchchalenge.viewmodel.state.SearchChallengeViewState
import kotlinx.coroutines.launch

class SearchChallengeViewModel(
    val searchChallengeViewState: SearchChallengeViewState,
    private val filterHistoryUseCase: FilterHistoryUseCase,
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
    private val logger: Logger,
) : ViewModel() {
    fun resolve(action: SearchChallengeViewAction) {
        viewModelScope.launch {
            when (action) {
                is SearchChallengeViewAction.Type -> handleType(action)
                is SearchChallengeViewAction.NavigateDetails -> navigateDetails(action)
            }
        }
    }

    private suspend fun navigateDetails(action: SearchChallengeViewAction.NavigateDetails) {
        searchChallengeViewState.action.emit(SearchChallengeViewState.Action.NavigateDetails(item = action.item))
    }

    private suspend fun handleType(action: SearchChallengeViewAction.Type) {
        handleHistory(action)
        handleResult(action)
    }

    private suspend fun handleResult(action: SearchChallengeViewAction.Type) {
        getSearchResultsUseCase(action.term).onSuccess {
            searchChallengeViewState.searchResult.emit(it.map {
                SearchResult(title = it.title)
            })
        }.onFailure {
            searchChallengeViewState.shouldShowError.emit(true)
            logger.registerLog(
                SearchChalengeLog.Error(
                    origin = this::class.java.simpleName,
                    error = it
                )
            )
        }
    }

    private suspend fun handleHistory(action: SearchChallengeViewAction.Type) {
        filterHistoryUseCase(action.term).onSuccess {
            searchChallengeViewState.searchHistory.emit(it)
        }.onFailure {
            logger.registerLog(
                SearchChalengeLog.Error(
                    origin = this::class.java.simpleName,
                    error = it
                )
            )
        }
    }
}