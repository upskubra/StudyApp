package com.kubrayildirim.studyapp.ui.study

import android.app.Application
import com.kubrayildirim.studyapp.data.repository.firestore.FireStoreRepository
import com.kubrayildirim.studyapp.ui.BaseViewModel
import com.kubrayildirim.studyapp.util.FirebaseState
import com.kubrayildirim.studyapp.util.StudyViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val repository: FireStoreRepository, application: Application
) : BaseViewModel(application) {


    private var _studyInfoState = MutableStateFlow(StudyViewState())
    var studyInfoState: StateFlow<StudyViewState> = _studyInfoState.asStateFlow()
    suspend fun addStudy(id: String, user: String, date: String, time: String) =
        repository.addStudyInfo(id, user, date, time).collect { result ->
            when (result) {
                is FirebaseState.Success -> {
                    result.data?.let {
                        _studyInfoState.update {
                            it.copy(
                                user = user, date = date, time = time, loading = false
                            )
                        }
                    }
                }
                is FirebaseState.Failure -> {
                    _studyInfoState.update {
                        it.copy(
                            loading = false, error = result.e ?: "An unexpected error occurred"
                        )
                    }
                }
                is FirebaseState.Loading -> {
                    _studyInfoState.update {
                        it.copy(loading = true)
                    }
                }
            }
        }
}