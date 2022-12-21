package com.kubrayildirim.studyapp.data.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.kubrayildirim.studyapp.util.FirebaseState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
  suspend  fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>>
}