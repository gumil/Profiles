package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.flow.Flow

interface GithubProfileRepository {
    fun getProfile(user: String): Flow<GithubUser>
}

fun GithubProfileRepository(apolloClient: ApolloClient): GithubProfileRepository {
    return ApolloGithubProfileRepository(apolloClient)
}
