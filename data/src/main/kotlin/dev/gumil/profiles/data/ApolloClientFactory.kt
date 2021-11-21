package dev.gumil.profiles.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCacheStore
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

fun apolloClient(
    cacheDir: String,
    token: String,
    serverUrl: String = "https://api.github.com/graphql",
): ApolloClient =
    ApolloClient.builder()
        .serverUrl(serverUrl)
        .httpCache(ApolloHttpCache(cacheStore(cacheDir)))
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(token))
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
        )
        .build()

@Suppress("MagicNumber")
private fun cacheStore(cacheDir: String): HttpCacheStore {
    val file = File(cacheDir, "apolloCache")
    // 10 MB cache
    val size: Long = 10 * 1024 * 1024
    return DiskLruHttpCacheStore(file, size)
}

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
