package com.rribesa.search.impl.data.datasource.local

interface GetSearchHistoryLocalDataSource {
    suspend fun getHistory(): Result<List<String>>
    suspend fun saveHistory(term: String): Result<Unit>
}