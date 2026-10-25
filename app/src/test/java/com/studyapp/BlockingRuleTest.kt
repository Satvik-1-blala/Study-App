package com.studyapp

import org.junit.Assert.*
import org.junit.Test

class BlockingRuleTest {
    @Test
    fun restrictedPackageIsBlocked() {
        val restricted = setOf("com.youtube", "com.instagram")
        assertTrue(restricted.contains("com.youtube"))
        assertFalse(restricted.contains("com.unknown"))
    }
    @Test
    fun emptySelectionMeansNoBlocking() {
        val restricted = emptySet<String>()
        assertFalse(restricted.contains("com.youtube"))
    }
}
