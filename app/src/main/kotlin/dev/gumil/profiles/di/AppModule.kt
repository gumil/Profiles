package dev.gumil.profiles.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gumil.profiles.BuildConfig
import dev.gumil.profiles.data.apolloClient
import dev.gumil.profiles.util.AndroidDispatcherProvider
import dev.gumil.profiles.util.DispatcherProvider
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApolloClient(
        @ApplicationContext context: Context
    ): ApolloClient {
        return apolloClient(
            cacheDir = File(context.filesDir, "apolloCache"),
            token = BuildConfig.TOKEN
        )
    }

    @Provides
    fun provideDispatcher(): DispatcherProvider = AndroidDispatcherProvider()
}
