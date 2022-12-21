package com.kubrayildirim.studyapp.ui.infolist

import android.app.Application
import com.kubrayildirim.studyapp.data.model.ListViewState
import com.kubrayildirim.studyapp.util.FirebaseState
import com.kubrayildirim.studyapp.data.repository.firestore.FireStoreRepository
import com.kubrayildirim.studyapp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StudyInfoListViewModel @Inject constructor(
    private val fireStoreRepository: FireStoreRepository,
    application: Application
) :
    BaseViewModel(application) {

    private var _studyInfoListState = MutableStateFlow(ListViewState())
    var studyInfoState: StateFlow<ListViewState> = _studyInfoListState.asStateFlow()

    suspend fun getStudyInfoList(user: String) =
        fireStoreRepository.getAllStudyInfo(user).collect { result ->
            when (result) {
                is FirebaseState.Success -> {
                    result.data?.let { list ->
                        _studyInfoListState.update {
                            it.copy(
                                studInfoList = list,
                                loading = false,
                                user = user
                            )
                        }
                    }
                }
                is FirebaseState.Failure -> {
                    _studyInfoListState.update {
                        it.copy(
                            loading = false,
                            error = result.e ?: "An unexpected error occurred"
                        )
                    }
                }
                is FirebaseState.Loading -> {
                    _studyInfoListState.update {
                        it.copy(loading = true)
                    }
                }
            }
        }

}