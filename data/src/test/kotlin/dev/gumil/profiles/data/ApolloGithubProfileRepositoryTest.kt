package dev.gumil.profiles.data

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class ApolloGithubProfileRepositoryTest {

    private val mockServer = MockWebServer()
    private val client = apolloClient(
        cacheDir = "",
        token = "",
        serverUrl = mockServer.url("/").toString()
    )
    private val repository = ApolloGithubProfileRepository(client)

    @Test
    fun `getProfile returns a GithubUser`() = runBlocking {
        val user = "${Random.nextInt()}"
        mockServer.enqueue(mockResponseFrom("getProfile.json"))

        val githubRepo = GithubRepository(
            name = "Kaskade",
            description = "Simplifying state management",
            stargazers = 227,
            language = GithubLanguage(
                name = "Kotlin",
                color = "#A97BFF"
            )
        )

        val expected = GithubUser(
            avatarUrl = "https://avatars.githubusercontent.com/u/aang",
            bio = "Android. Kotlin.",
            followers = 30,
            following = 65,
            email = "miguelrpanelo@gmail.com",
            name = "Miguel Panelo",
            login = "gumil",
            pinnedRepositories = listOf(githubRepo),
            topRepositories = listOf(githubRepo),
            starredRepositories = listOf(githubRepo)

        )

        val actual = repository.getProfile(user)

        assertEquals(expected, actual)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}
