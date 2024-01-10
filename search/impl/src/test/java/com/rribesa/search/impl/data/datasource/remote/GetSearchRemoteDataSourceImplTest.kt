package com.rribesa.search.impl.data.datasource.remote

import com.rribesa.search.impl.data.service.api.SearchResultAPI
import com.rribesa.search.impl.data.service.response.SearchResultResponse
import com.rribesa.search.utils.CoroutinesRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetSearchRemoteDataSourceImplTest {
    @get:Rule
    val coroutinesRule = CoroutinesRule()
    private val api: SearchResultAPI = mockk()
    private val remoteDataSource = GetSearchRemoteDataSourceImpl(api)

    @Test
    fun `getSearchResult return success from api`() = runTest {
        prepareSuccessScenario(SearchResultResponse("test"))
        val result = remoteDataSource.getSearchResult("test")
        assertEquals(Result.success(SearchResultResponse("test")), result)
    }

    @Test
    fun `getSearchResult return error from api`() = runTest {
        val exception = NullPointerException()
        prepareFailureScenario(exception)
        val result = remoteDataSource.getSearchResult("test")
        val expected: Result<SearchResultResponse> = Result.failure(exception)
        assertEquals(expected, result)
    }

    private fun prepareSuccessScenario(
        response: SearchResultResponse = SearchResultResponse("test")
    ) {
        coEvery { api.getSearchResults(any()) } returns response
    }

    private fun prepareFailureScenario(
        error: Throwable
    ) {
        coEvery { api.getSearchResults(any()) } throws error
    }
}