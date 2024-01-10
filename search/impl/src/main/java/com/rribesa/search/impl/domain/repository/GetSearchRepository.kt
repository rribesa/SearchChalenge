package com.rribesa.search.impl.domain.repository

import com.rribesa.search.domain.model.SearchResultModel

interface GetSearchRepository {
    suspend fun getSearchHistory(term:String): Result<List<SearchResultModel>>
}