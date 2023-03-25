package com.blueprint.simplekotlinflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountdownTimerViewModel : ViewModel() {

    private var countdownJob: Job? = null
    private val _countdownTimeLeft = MutableStateFlow(0)
    val countdownTimeLeft: StateFlow<Int> = _countdownTimeLeft
    /**
    A MutableSharedFlow is used to represent the countdown finished event as a shared flow that can
    emit a single event to multiple collectors.

    The reason for using a shared flow instead of a regular flow or a LiveData is because a shared
    flow is designed specifically for emitting events to multiple collectors, which is important for
    cases where we need to handle events in multiple places, such as showing a toast message or
    updating UI elements in different parts of the app.

    Another advantage of using a shared flow is that it allows emitting events even if there are no
    collectors attached to it at the moment. This means that we can emit the countdown finished
    event before the Fragment starts observing the shared flow, and still receive it when we start
    observing.

    Using a MutableSharedFlow in this implementation allows representing the countdown
    finished event as a shared flow that can emit a single event to multiple collectors, even if
    there are no collectors attached at the moment.
     */
    private val _countdownFinishedEvent = MutableSharedFlow<Unit>()
    val countdownFinishedEvent: SharedFlow<Unit> = _countdownFinishedEvent

    fun startCountdown(timeInSeconds: Int) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            _countdownTimeLeft.value = timeInSeconds
            while (_countdownTimeLeft.value > 0) {
                delay(1000)
                _countdownTimeLeft.value -= 1
            }
            _countdownFinishedEvent.emit(Unit)
        }
    }

    fun cancelCountdown() {
        countdownJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}