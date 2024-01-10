package com.rribesa.search.impl.domain.repository

import com.rribesa.search.domain.model.SearchHistoryError
import com.rribesa.search.domain.model.SearchResultError
import com.rribesa.search.domain.model.SearchResultModel
import com.rribesa.search.impl.data.datasource.local.GetSearchHistoryLocalDataSource
import com.rribesa.search.impl.data.datasource.remote.GetSearchRemoteDataSource

class GetSearchRepositoryImpl(
    private val searchRemoteDataSource: GetSearchRemoteDataSource,
    private val searchHistoryLocalDataSource: GetSearchHistoryLocalDataSource,
) : GetSearchRepository {
    override suspend fun getSearchResults(term: String): Result<List<SearchResultModel>> =
        searchRemoteDataSource.getSearchResult(term).map {
            listOf(SearchResultModel(it.title))
        }.onFailure {
            return Result.failure(SearchResultError.NetworkError(it))
        }.onSuccess {
            saveHistory(term)
        }

    override suspend fun getHistory(): Result<List<String>> =
        searchHistoryLocalDataSource.getHistory().onFailure {
            return Result.failure(SearchHistoryError.GetHistoryError(it))
        }

    private suspend fun saveHistory(term: String) {
        searchHistoryLocalDataSource.saveHistory(term)
    }
}