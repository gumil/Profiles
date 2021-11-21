package dev.gumil.profiles.data

interface GithubProfileRepository {
    suspend fun getProfile(user: String): GithubUser?
}
