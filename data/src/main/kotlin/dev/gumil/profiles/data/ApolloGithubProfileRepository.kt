package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.coroutines.await
import java.util.concurrent.TimeUnit

internal class ApolloGithubProfileRepository(
    private val apolloClient: ApolloClient
) : GithubProfileRepository {

    override suspend fun getProfile(user: String): GithubUser? {
        return apolloClient.query(GetProfileQuery(user))
            .toBuilder()
            .httpCachePolicy(
                HttpCachePolicy.NETWORK_FIRST
                    .expireAfter(1, TimeUnit.DAYS)
            )
            .build()
            .await()
            .data
            ?.user
            ?.toGithubUser()
    }
}
