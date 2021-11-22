package dev.gumil.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gumil.profiles.data.GithubProfileRepository
import dev.gumil.profiles.data.GithubUser
import dev.gumil.profiles.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GithubProfileRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val userFlow: Flow<UiState>
        get() = mutableFlow.filterNotNull()

    private val mutableFlow = MutableStateFlow(UiState())

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            mutableFlow.value = mutableFlow.value.copy(isRefreshing = true)
            runCatching {
                withContext(dispatcherProvider.io) {
                    repository.getProfile("gumil")
                }
            }.onSuccess { user ->
                mutableFlow.value = UiState(user, false)
            }.onFailure {
                mutableFlow.value = UiState(hasError = true)
            }
        }
    }

    data class UiState(
        val user: GithubUser? = null,
        val isRefreshing: Boolean = true,
        val hasError: Boolean = false
    )
}
