package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

private const val TOKEN = ""

private val apolloClient = ApolloClient.builder()
    .serverUrl("https://api.github.com/graphql")
    .okHttpClient(
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(TOKEN))
            .build()
    )
    .build()

private class AuthorizationInterceptor(
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}

fun main() = runBlocking {
    GlobalScope.launch {
        val response = apolloClient.query(GetProfileQuery()).await()
        println(response)
    }.join()
}
