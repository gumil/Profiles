package dev.gumil.profiles.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.gumil.profiles.data.GithubProfileRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideRepository(
        apolloClient: ApolloClient
    ): GithubProfileRepository {
        return GithubProfileRepository(apolloClient)
    }
}
