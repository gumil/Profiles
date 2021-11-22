package dev.gumil.profiles

import dev.gumil.profiles.data.GithubProfileRepository
import dev.gumil.profiles.ui.githubUser
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

class MainViewModelTest {
    private val repository = mock<GithubProfileRepository>()
    private val dispatcherProvider = TestDispatcherProvider()
    private val viewModel = MainViewModel(repository, dispatcherProvider)

    @Test
    fun `getProfile returns a GithubUser`() = runBlocking {
        val user = "${Random.nextInt()}"
        val expected = githubUser()

        whenever(repository.getProfile(user)).thenReturn(flowOf(expected))

        viewModel.getProfile(user).collect { actual ->
            assertEquals(expected, actual)
        }
    }
}
