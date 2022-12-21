package com.kubrayildirim.studyapp.util

data class StudyViewState(
    var loading: Boolean = false,
    var error: String = "",
    var success: Boolean? = false,
    var date: String = "",
    var time: String = "",
    var user: String? = null,
)
