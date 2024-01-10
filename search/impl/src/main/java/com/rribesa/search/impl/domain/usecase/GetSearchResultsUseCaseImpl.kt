package com.rribesa.search.impl.domain.usecase

import com.rribesa.search.domain.model.SearchResultModel
import com.rribesa.search.domain.usecase.GetSearchResultsUseCase
import com.rribesa.search.impl.domain.repository.GetSearchRepository

class GetSearchResultsUseCaseImpl(
    private val repository: GetSearchRepository,
) : GetSearchResultsUseCase {
    override suspend fun invoke(term: String): Result<List<SearchResultModel>> =
        repository.getSearchResults(term)
}