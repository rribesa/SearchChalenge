package com.rribesa.search.impl.data.datasource.remote

import com.rribesa.search.impl.data.service.api.SearchResultAPI
import com.rribesa.search.impl.data.service.response.SearchResultResponse

class GetSearchRemoteDataSourceImpl(
    private val api: SearchResultAPI,
) : GetSearchRemoteDataSource {
    override suspend fun getSearchResult(term: String): Result<SearchResultResponse> =
        runCatching {
            api.getSearchResults(term)
        }
}