package com.studyapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TimerStateTest {
    private val testDispatcher = TestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun startPauseResumeReset() = runTest {
        val vm = FocusViewModel()
        assertEquals(25 * 60, vm.remaining.value)
        assertFalse(vm.running.value)

        vm.start()
        assertTrue(vm.running.value)

        advanceTimeBy(5_000)
        // tick logic lives in viewModel, not tested here; state flow updates via tick()
        vm.tick()
        assertEquals(25 * 60 - 1, vm.remaining.value)

        vm.pause()
        assertFalse(vm.running.value)
        val paused = vm.remaining.value
        advanceTimeBy(10_000)
        assertEquals(paused, vm.remaining.value)

        vm.start()
        assertTrue(vm.running.value)
        advanceTimeBy(5_000)
        vm.tick()
        assertEquals(paused - 1, vm.remaining.value)

        vm.reset()
        assertEquals(25 * 60, vm.remaining.value)
        assertFalse(vm.running.value)
    }

    @Test
    fun stopAtZero() = runTest {
        val vm = FocusViewModel()
        vm.reset(2)
        vm.start()
        vm.tick()
        assertEquals(1, vm.remaining.value)
        vm.tick()
        assertEquals(0, vm.remaining.value)
        // should not go negative
        vm.tick()
        assertEquals(0, vm.remaining.value)
        assertFalse(vm.running.value)
    }

    @Test
    fun customDuration() = runTest {
        val vm = FocusViewModel()
        vm.reset(10 * 60)
        assertEquals(10 * 60, vm.remaining.value)
        vm.start()
        vm.tick()
        assertEquals(10 * 60 - 1, vm.remaining.value)
    }
}
