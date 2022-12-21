package com.kubrayildirim.studyapp.data.datasource.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kubrayildirim.studyapp.util.FirebaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    val auth: FirebaseAuth
) : AuthDataSource {
    override suspend fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>> = flow {
        val user = auth.currentUser
        if (user != null) {
            emit(FirebaseState.Success(user))
        } else {
            auth.signInAnonymously().result?.let {
                if (it.user != null) {
                    emit(FirebaseState.Success(it.user!!))
                } else {
                    emit(FirebaseState.Failure("Error"))
                }
            }
        }
    }
}

