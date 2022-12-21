package com.kubrayildirim.studyapp.data.datasource.auth

import com.google.firebase.auth.FirebaseUser
import com.kubrayildirim.studyapp.util.FirebaseState
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
  suspend  fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>>
}
