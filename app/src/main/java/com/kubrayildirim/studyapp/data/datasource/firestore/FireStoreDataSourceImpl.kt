package com.kubrayildirim.studyapp.data.datasource.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.kubrayildirim.studyapp.data.model.StudyInfo
import com.kubrayildirim.studyapp.util.FirebaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreDataSourceImpl @Inject constructor(
    private val storage: FirebaseFirestore,
) : FireStoreDataSource {
    override suspend fun getAllStudyInfoList(user: String): Flow<FirebaseState<MutableList<StudyInfo>>> =
        flow {
            emit(FirebaseState.Loading)
            try {
                val result = storage.collection("users").document(user).collection("studyInfo")
                    .get().await()
                val list = mutableListOf<StudyInfo>()
                for (document in result) {
                    val studyInfo = document.id.let {
                        StudyInfo(
                            id = it,
                            user = document.data["user"].toString(),
                            date = document.data["date"].toString(),
                            time = document.data["time"].toString()
                        )
                    }
                    if (studyInfo != null) {
                        list.add(studyInfo)
                    }

                }
                emit(FirebaseState.Success(list))
            } catch (e: Exception) {
                emit(FirebaseState.Failure(e.localizedMessage))
            }
        }

    override suspend fun addStudyInfo(
        id: String,
        user: String,
        date: String,
        time: String
    ) = flow {
        try {
            val studyInfo = StudyInfo(id, user, date, time)
            storage.collection("users").document(user).collection("studyInfo")
                .document(studyInfo.id)
                .set(studyInfo).await()


            emit(FirebaseState.Success(studyInfo))
        } catch (e: Exception) {
            emit(FirebaseState.Failure(e.localizedMessage))
        }
    }
}
