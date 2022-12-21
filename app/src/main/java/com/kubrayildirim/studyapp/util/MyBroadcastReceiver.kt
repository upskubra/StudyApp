package com.kubrayildirim.studyapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver (val showState: (state: Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getBooleanExtra("state", false)
        showState(state)
    }
}