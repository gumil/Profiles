package dev.gumil.profiles

import app.cash.turbine.test
import dev.gumil.profiles.data.GithubProfileRepository
import dev.gumil.profiles.ui.githubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val repository = mock<GithubProfileRepository>()
    private val dispatcherProvider = TestDispatcherProvider()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcherProvider.main)
    }

    @Test
    fun `getProfile when refresh is called should emit a UiState`() = runBlockingTest(dispatcherProvider.main) {
        val githubUser = githubUser()
        whenever(repository.getProfile(any())).thenReturn(githubUser)

        val viewModel = MainViewModel(repository, dispatcherProvider)

        viewModel.userFlow.test {
            // first result from constructor refresh
            assertEquals(MainViewModel.UiState(githubUser, false), awaitItem())

            viewModel.refresh()

            // call to set refresh to true
            assertEquals(MainViewModel.UiState(githubUser, true), awaitItem())
            // calls to refresh again
            assertEquals(MainViewModel.UiState(githubUser, false), awaitItem())
            assertEquals(0, cancelAndConsumeRemainingEvents().size)
        }
    }

    @Test
    @Suppress("TooGenericExceptionThrown")
    fun `getProfile when an error is occured`() = runBlockingTest(dispatcherProvider.main) {
        whenever(repository.getProfile(any())).then { throw RuntimeException() }

        val viewModel = MainViewModel(repository, dispatcherProvider)

        viewModel.userFlow.test {
            // first result from constructor refresh
            assertEquals(MainViewModel.UiState(hasError = true), awaitItem())
            assertEquals(0, cancelAndConsumeRemainingEvents().size)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
