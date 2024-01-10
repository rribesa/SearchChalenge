package com.rribesa.searchchalenge.viewmodel.action

import com.rribesa.searchchalenge.ui.model.SearchResult

sealed class SearchChallengeViewAction {
    data class Type(val term: String) : SearchChallengeViewAction()
    data class NavigateDetails(val item: SearchResult) : SearchChallengeViewAction()
}
