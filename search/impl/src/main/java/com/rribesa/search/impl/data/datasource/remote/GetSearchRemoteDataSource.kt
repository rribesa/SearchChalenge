package com.rribesa.search.impl.data.datasource.remote

import com.rribesa.search.impl.data.service.response.SearchResultResponse

interface GetSearchRemoteDataSource {
    suspend fun getSearchResult(term:String):Result<SearchResultResponse>
}