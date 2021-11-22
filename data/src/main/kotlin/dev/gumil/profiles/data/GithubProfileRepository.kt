package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient

interface GithubProfileRepository {
    suspend fun getProfile(user: String): GithubUser?
}

fun GithubProfileRepository(apolloClient: ApolloClient): GithubProfileRepository {
    return ApolloGithubProfileRepository(apolloClient)
}
