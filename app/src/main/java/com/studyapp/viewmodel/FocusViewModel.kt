package com.studyapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.studyapp.data.DatabaseProvider
import com.studyapp.data.SessionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FocusViewModel : ViewModel() {
    private val _remaining = MutableStateFlow(25 * 60)
    val remaining: StateFlow<Int> = _remaining
    private val _running = MutableStateFlow(false)
    val running: StateFlow<Boolean> = _running

    fun start() { _running.value = true }
    fun pause() { _running.value = false }
    fun reset(seconds: Int = 25 * 60) { _running.value = false; _remaining.value = seconds }
    fun tick() { if (_running.value && _remaining.value > 0) _remaining.value-- }

    fun saveCompletedSession(context: Context, subject: String, durationSeconds: Int, startedAt: Long) {
        val db = DatabaseProvider.get(context)
        db.sessionDao().insert(SessionEntity(subject = subject, durationSeconds = durationSeconds, startedAt = startedAt))
    }
}
