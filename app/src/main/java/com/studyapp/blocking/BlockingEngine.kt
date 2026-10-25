package com.studyapp.blocking

interface BlockingEngine {
    suspend fun isRestricted(packageName: String): Boolean
    suspend fun onRestrictedAppDetected(packageName: String, sessionRemainingSeconds: Int)
    fun status(): BlockingStatus
}

enum class BlockingStatus { FULL, DEGRADED, DISABLED }

class NoOpBlockingEngine : BlockingEngine {
    override suspend fun isRestricted(packageName: String) = false
    override suspend fun onRestrictedAppDetected(packageName: String, sessionRemainingSeconds: Int) {}
    override fun status() = BlockingStatus.DISABLED
}
