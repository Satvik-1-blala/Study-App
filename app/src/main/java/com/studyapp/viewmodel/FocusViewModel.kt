package com.studyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FocusViewModel : ViewModel() {
    private val _remaining = MutableStateFlow(25 * 60)
    val remaining: StateFlow<Int> = _remaining
    private val _running = MutableStateFlow(false)
    val running: StateFlow<Boolean> = _running

    fun start() { _running.value = true }
    fun pause() { _running.value = false }
    fun reset(seconds: Int = 25 * 60) { _running.value = false; _remaining.value = seconds }
    fun tick() { if (_running.value && _remaining.value > 0) _remaining.value-- }
}
