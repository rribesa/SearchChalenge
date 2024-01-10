package com.rribesa.search.impl.domain.repository

import com.rribesa.search.domain.model.SearchHistoryError
import com.rribesa.search.domain.model.SearchResultError
import com.rribesa.search.domain.model.SearchResultModel
import com.rribesa.search.impl.data.datasource.local.GetSearchHistoryLocalDataSource
import com.rribesa.search.impl.data.datasource.remote.GetSearchRemoteDataSource
import com.rribesa.search.impl.data.service.response.SearchResultResponse
import com.rribesa.search.utils.CoroutinesRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
internal class GetSearchRepositoryImplTest {
    @get:Rule
    val coroutinesRule = CoroutinesRule()
    private val searchRemoteDataSource: GetSearchRemoteDataSource = mockk()
    private val searchHistoryLocalDataSource: GetSearchHistoryLocalDataSource = mockk()
    private val repository = GetSearchRepositoryImpl(
        searchRemoteDataSource = searchRemoteDataSource,
        searchHistoryLocalDataSource = searchHistoryLocalDataSource
    )

    @Test
    fun `getSearchResult return List from search dataSource`() = runTest {
        prepareScenario(searchResult = Result.success(SearchResultResponse("test")))
        val result = repository.getSearchResults("test")
        assertEquals(Result.success(listOf(SearchResultModel("test"))), result)
    }

    @Test
    fun `getSearchResult return error from search dataSource`() = runTest {
        val exception = NullPointerException()
        prepareScenario(searchResult = Result.failure(exception))
        val result = repository.getSearchResults("test")
        val expected: Result<SearchResultModel> =
            Result.failure(SearchResultError.NetworkError(exception))
        assertEquals(expected, result)
    }

    @Test
    fun `getSearchResult save history when datasource return succes`() = runTest {
        prepareScenario(searchResult = Result.success(SearchResultResponse("test")))
        repository.getSearchResults("test")
        coVerify {
            searchHistoryLocalDataSource.saveHistory("test")
        }
    }

    @Test
    fun `getHistory return list from historyDataSource`() = runTest {
        prepareScenario(historyResult = Result.success(emptyList()))
        val result = repository.getHistory()
        assertEquals(Result.success(emptyList<String>()), result)
    }

    @Test
    fun `getHistory return error from historyDataSource`() = runTest {
        val exception = NullPointerException()
        prepareScenario(historyResult = Result.failure(exception))
        val result = repository.getHistory()
        val expected: Result<List<String>> =
            Result.failure(SearchHistoryError.GetHistoryError(exception))
        assertEquals(expected, result)
    }

    private fun prepareScenario(
        saveResult: Result<Unit> = Result.success(Unit),
        historyResult: Result<List<String>> = Result.success(emptyList()),
        searchResult: Result<SearchResultResponse> = Result.success(SearchResultResponse(""))
    ) {
        coEvery { searchHistoryLocalDataSource.saveHistory(any()) } returns saveResult
        coEvery { searchHistoryLocalDataSource.getHistory() } returns historyResult
        coEvery { searchRemoteDataSource.getSearchResult(any()) } returns searchResult
    }
}