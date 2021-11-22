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
import kotlinx.coroutines.flow.flowOn
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
            val user = withContext(dispatcherProvider.io) {
                repository.getProfile("gumil")
            }
            mutableFlow.value = UiState(user, false)
        }
    }

    data class UiState(
        val user: GithubUser? = null,
        val isRefreshing: Boolean = true
    )
}
