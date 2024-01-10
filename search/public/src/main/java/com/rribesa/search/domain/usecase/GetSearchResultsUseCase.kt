package com.rribesa.search.domain.usecase

import com.rribesa.search.domain.model.SearchResultModel

interface GetSearchResultsUseCase {
    suspend operator fun invoke(term: String): Result<List<SearchResultModel>>
}