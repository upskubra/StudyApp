package com.kubrayildirim.studyapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kubrayildirim.studyapp.data.datasource.auth.AuthDataSource
import com.kubrayildirim.studyapp.data.datasource.auth.AuthDataSourceImpl
import com.kubrayildirim.studyapp.data.datasource.firestore.FireStoreDataSource
import com.kubrayildirim.studyapp.data.datasource.firestore.FireStoreDataSourceImpl
import com.kubrayildirim.studyapp.data.repository.auth.AuthRepository
import com.kubrayildirim.studyapp.data.repository.auth.AuthRepositoryImpl
import com.kubrayildirim.studyapp.data.repository.firestore.FireStoreRepository
import com.kubrayildirim.studyapp.data.repository.firestore.FireStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesAuthDataSource(auth: FirebaseAuth): AuthDataSource {
        return AuthDataSourceImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFireStoreDataSource(fireStore: FirebaseFirestore): FireStoreDataSource {
        return FireStoreDataSourceImpl(fireStore)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }

    @Provides
    @Singleton
    fun provideFireStoreRepository(fireStoreDataSource: FireStoreDataSource): FireStoreRepository {
        return FireStoreRepositoryImpl(fireStoreDataSource)
    }
}
