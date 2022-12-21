package com.kubrayildirim.studyapp.data.repository.firestore

import com.kubrayildirim.studyapp.data.datasource.firestore.FireStoreDataSource
import javax.inject.Inject

class FireStoreRepositoryImpl @Inject constructor(
    private val fireStoreDataSource: FireStoreDataSource
) : FireStoreRepository {
    override suspend fun getAllStudyInfo(user: String) =
        fireStoreDataSource.getAllStudyInfoList(user)

    override suspend fun addStudyInfo(id: String, user: String, date: String, time: String) =
        fireStoreDataSource.addStudyInfo(id, user, date, time)
}
