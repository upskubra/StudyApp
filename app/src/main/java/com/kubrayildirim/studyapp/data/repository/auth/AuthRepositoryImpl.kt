package com.kubrayildirim.studyapp.data.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.kubrayildirim.studyapp.data.datasource.auth.AuthDataSource
import com.kubrayildirim.studyapp.util.FirebaseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>> {
        return authDataSource.anonymousSignIn()
    }
}
