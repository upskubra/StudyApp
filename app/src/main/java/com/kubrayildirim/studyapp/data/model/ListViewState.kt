package com.kubrayildirim.studyapp.data.model

data class ListViewState(
    var loading: Boolean = false,
    var error: String = "",
    var success: Boolean? = false,
    var user: String? = null,
    var studInfoList: MutableList<StudyInfo>? = arrayListOf(),
)
