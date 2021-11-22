package dev.gumil.profiles

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gumil.profiles.data.GithubProfileRepository
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GithubProfileRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    fun getProfile(user: String): Flow<GithubUser> {
        return repository.getProfile(user)
            .flowOn(dispatcherProvider.io)
    }
}
