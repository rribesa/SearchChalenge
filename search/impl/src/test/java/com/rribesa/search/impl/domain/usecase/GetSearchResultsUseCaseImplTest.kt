package com.rribesa.search.impl.domain.usecase

import com.rribesa.search.domain.model.SearchResultModel
import com.rribesa.search.impl.domain.repository.GetSearchRepository
import com.rribesa.search.utils.CoroutinesRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class GetSearchResultsUseCaseImplTest {
    @get:Rule
    val coroutinesRule = CoroutinesRule()
    private val repository: GetSearchRepository = mockk()
    private val useCaseImpl = GetSearchResultsUseCaseImpl(repository = repository)

    @Test
    fun `invoke return result from repository`() = runTest {
        val expectedResult = Result.success(listOf(SearchResultModel("test")))
        prepareScenario(getSearchResult = expectedResult)
        val result = useCaseImpl("test")
        assertEquals(expectedResult, result)
    }


    private fun prepareScenario(
        getSearchResult: Result<List<SearchResultModel>> = Result.success(listOf(SearchResultModel(""))),
    ) {
        coEvery {
            repository.getSearchResults(any())
        } returns getSearchResult
    }

}