package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

private val apolloClient = ApolloClient.builder()
    .serverUrl("https://api.github.com/graphql")
    .okHttpClient(
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .build()
    )
    .build()

private class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer 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")
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
