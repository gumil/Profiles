package dev.gumil.profiles.data

import kotlinx.coroutines.flow.Flow

interface GithubProfileRepository {
    fun getProfile(user: String): Flow<GithubUser>
}
