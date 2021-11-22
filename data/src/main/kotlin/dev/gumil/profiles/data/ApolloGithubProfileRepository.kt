package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.coroutines.toFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
internal class ApolloGithubProfileRepository(
    private val apolloClient: ApolloClient
) : GithubProfileRepository {

    override fun getProfile(user: String): Flow<GithubUser> {
        return apolloClient.query(GetProfileQuery(user))
            .toBuilder()
            .httpCachePolicy(
                HttpCachePolicy.NETWORK_FIRST
                    .expireAfter(1, TimeUnit.DAYS)
            )
            .build()
            .toFlow()
            .mapNotNull { response ->
                response.data?.user?.toGithubUser()
            }
    }
}
