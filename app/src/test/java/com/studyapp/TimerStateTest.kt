package com.studyapp

import com.studyapp.viewmodel.FocusViewModel
import org.junit.Assert.*
import org.junit.Test

class TimerStateTest {
    @Test
    fun startPauseResumeReset() {
        val vm = FocusViewModel()
        assertEquals(25 * 60, vm.remaining.value)
        assertFalse(vm.running.value)

        vm.start()
        assertTrue(vm.running.value)

        vm.tick()
        assertEquals(25 * 60 - 1, vm.remaining.value)

        vm.pause()
        assertFalse(vm.running.value)
        val paused = vm.remaining.value
        assertEquals(paused, vm.remaining.value)

        vm.start()
        assertTrue(vm.running.value)
        vm.tick()
        assertEquals(paused - 1, vm.remaining.value)

        vm.reset()
        assertEquals(25 * 60, vm.remaining.value)
        assertFalse(vm.running.value)
    }

    @Test
    fun stopAtZero() {
        val vm = FocusViewModel()
        vm.reset(2)
        vm.start()
        vm.tick()
        assertEquals(1, vm.remaining.value)
        vm.tick()
        assertEquals(0, vm.remaining.value)
        vm.tick()
        assertEquals(0, vm.remaining.value)
        assertFalse(vm.running.value)
    }

    @Test
    fun customDuration() {
        val vm = FocusViewModel()
        vm.reset(10 * 60)
        assertEquals(10 * 60, vm.remaining.value)
        vm.start()
        vm.tick()
        assertEquals(10 * 60 - 1, vm.remaining.value)
    }
}
