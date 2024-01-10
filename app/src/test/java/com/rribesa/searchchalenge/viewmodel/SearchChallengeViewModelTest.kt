package com.rribesa.searchchalenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rribesa.logs.Logger
import com.rribesa.search.domain.model.SearchResultModel
import com.rribesa.search.domain.usecase.FilterHistoryUseCase
import com.rribesa.search.domain.usecase.GetSearchResultsUseCase
import com.rribesa.searchchalenge.ui.model.SearchResult
import com.rribesa.searchchalenge.utils.CoroutinesRule
import com.rribesa.searchchalenge.viewmodel.action.SearchChallengeViewAction
import com.rribesa.searchchalenge.viewmodel.state.SearchChallengeViewState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchChallengeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    private val filterHistoryUseCase: FilterHistoryUseCase = mockk()
    private val getSearchResultsUseCase: GetSearchResultsUseCase = mockk()
    private val logger: Logger = mockk()
    private lateinit var searchChallengeViewModel: SearchChallengeViewModel

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `resolve action type return history from usecase`() = runBlocking {
        val viewState = SearchChallengeViewState()
        prepareScenario(
            filterResult = Result.success(listOf("test", "test2")),
            searchChallengeViewState = viewState
        )
        searchChallengeViewModel.resolve(SearchChallengeViewAction.Type("t"))
        assertEquals(listOf("test", "test2"), viewState.searchHistory.value)
    }

    @Test
    fun `resolve action type return show empty when result useCase return empty`() = runTest {
        val viewState = SearchChallengeViewState()
        prepareScenario(
            searchResult = Result.success(emptyList()),
            searchChallengeViewState = viewState
        )
        searchChallengeViewModel.resolve(SearchChallengeViewAction.Type("t"))
        assertTrue(viewState.isEmptyState.value)
    }

    @Test
    fun `resolve action type return show error when result useCase return result failure`() =
        runTest {
            val viewState = SearchChallengeViewState()
            prepareScenario(
                searchResult = Result.failure(NullPointerException()),
                searchChallengeViewState = viewState
            )
            searchChallengeViewModel.resolve(SearchChallengeViewAction.Type("t"))
            assertTrue(viewState.shouldShowError.value)
        }

    @Test
    fun `resolve action type return result when result useCase return items`() =
        runTest {
            val viewState = SearchChallengeViewState()
            prepareScenario(
                searchResult = Result.success(listOf(SearchResultModel("test"))),
                searchChallengeViewState = viewState
            )
            searchChallengeViewModel.resolve(SearchChallengeViewAction.Type("t"))
            assertEquals(listOf(SearchResult("test")), viewState.searchResult.value)
        }

    @Test
    fun `resolve action navigateDetails emit state to navigate detail screen`() =
        runTest {
            val viewState: SearchChallengeViewState = mockk()
            val action: MutableSharedFlow<SearchChallengeViewState.Action> = mockk()
            coEvery { viewState.action } returns action
            coEvery { action.emit(any()) } just runs
            prepareScenario(
                searchResult = Result.success(listOf(SearchResultModel("test"))),
                searchChallengeViewState = viewState
            )
            searchChallengeViewModel.resolve(
                SearchChallengeViewAction.NavigateDetails(
                    SearchResult(
                        "test"
                    )
                )
            )
            coVerify {
                action.emit(
                    SearchChallengeViewState.Action.NavigateDetails(
                        SearchResult(
                            "test"
                        )
                    )
                )
            }
        }


    private fun prepareScenario(
        filterResult: Result<List<String>> = Result.success(emptyList()),
        searchResult: Result<List<SearchResultModel>> = Result.success(emptyList()),
        searchChallengeViewState: SearchChallengeViewState = mockk()
    ) {
        coEvery {
            filterHistoryUseCase(any())
        } returns filterResult
        coEvery { getSearchResultsUseCase(any()) } returns searchResult
        coEvery { logger.registerLog(any()) } just runs
        searchChallengeViewModel = SearchChallengeViewModel(
            searchChallengeViewState = searchChallengeViewState,
            filterHistoryUseCase = filterHistoryUseCase,
            getSearchResultsUseCase = getSearchResultsUseCase,
            logger = logger
        )
    }
}