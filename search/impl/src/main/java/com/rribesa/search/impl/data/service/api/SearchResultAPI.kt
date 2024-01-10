package com.rribesa.search.impl.data.service.api

import com.rribesa.search.impl.data.service.response.SearchResultResponse

interface SearchResultAPI {
    suspend fun getSearchResults(term: String): SearchResultResponse
}