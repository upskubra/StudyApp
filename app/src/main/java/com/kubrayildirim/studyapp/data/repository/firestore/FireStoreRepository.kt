package com.kubrayildirim.studyapp.data.repository.firestore

import com.kubrayildirim.studyapp.data.model.StudyInfo
import com.kubrayildirim.studyapp.util.FirebaseState
import kotlinx.coroutines.flow.Flow


interface FireStoreRepository {
    suspend fun getAllStudyInfo(user: String): Flow<FirebaseState<MutableList<StudyInfo>>>
    suspend fun addStudyInfo(
        id : String,
        user: String,
        date: String,
        time: String
    ): Flow<FirebaseState<StudyInfo>>


}