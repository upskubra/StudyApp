package com.kubrayildirim.studyapp.ui.activity

import android.app.Application
import com.kubrayildirim.studyapp.util.FirebaseState
import com.kubrayildirim.studyapp.data.model.AuthViewState
import com.kubrayildirim.studyapp.data.repository.auth.AuthRepository
import com.kubrayildirim.studyapp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : BaseViewModel(application) {

    private val _authState = MutableStateFlow(AuthViewState())
    val authState: StateFlow<AuthViewState> = _authState.asStateFlow()

    suspend fun signIn() {
        authRepository.anonymousSignIn().collect { state ->
            when (state) {
                is FirebaseState.Success -> {
                    _authState.value =
                        state.data?.let {
                            AuthViewState(
                                user = it,
                                loading = false
                            )
                        }!!
                }
                is FirebaseState.Failure -> {
                    _authState.update {
                        it.copy(
                            loading = false,
                            error = it.error
                        )
                    }
                }
                is FirebaseState.Loading -> {
                    _authState.update {
                        it.copy(loading = true)
                    }
                }
            }
        }
    }
}