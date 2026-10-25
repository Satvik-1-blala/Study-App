package com.studyapp

import org.junit.Assert.assertEquals
import org.junit.Test

class TimerMathTest {
    @Test
    fun formatSeconds() {
        val s = 125
        assertEquals("02:05", String.format("%02d:%02d", s / 60, s % 60))
    }
    @Test
    fun decrementStopsAtZero() {
        var remaining = 2
        while (remaining > 0) remaining--
        assertEquals(0, remaining)
    }
}
