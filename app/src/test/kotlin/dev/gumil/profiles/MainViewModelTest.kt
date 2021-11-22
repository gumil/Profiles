package dev.gumil.profiles

import app.cash.turbine.test
import dev.gumil.profiles.data.GithubProfileRepository
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.ui.githubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

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
        val user = "gumil"
        val githubUser = githubUser()
        whenever(repository.getProfile(user)).thenReturn(githubUser)

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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
