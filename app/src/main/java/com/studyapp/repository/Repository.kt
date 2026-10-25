package com.studyapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.studyapp.data.*

// Repository interfaces for clean architecture
interface SessionRepository {
    suspend fun insert(s: SessionEntity)
    val allSessions: LiveData<List<SessionEntity>>
    suspend fun totalSince(since: Long): Int
}

interface BlockedAppRepository {
    suspend fun upsert(b: BlockedAppEntity)
    suspend fun remove(pkg: String)
    val allApps: LiveData<List<BlockedAppEntity>>
}

interface TaskRepository {
    suspend fun insert(t: TaskEntity)
    val allTasks: LiveData<List<TaskEntity>>
    suspend fun setDone(id: Long, done: Boolean)
}

interface ScheduleRepository {
    suspend fun insert(s: ScheduleEntity)
    suspend fun delete(id: Long)
    val allSchedules: LiveData<List<ScheduleEntity>>
}

// DAO implementations with Room
@Dao
interface SessionDao {
    @Insert fun insert(s: SessionEntity): Long
    @Query("SELECT * FROM sessions ORDER BY startedAt DESC") fun allSessions(): LiveData<List<SessionEntity>>
    @Query("SELECT COALESCE(SUM(durationSeconds),0) FROM sessions WHERE startedAt >= :since") suspend fun totalSince(since: Long): Int
}

@Dao
interface BlockedAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(b: BlockedAppEntity)
    @Query("DELETE FROM blocked_apps WHERE packageName=:pkg") suspend fun remove(pkg: String)
    @Query("SELECT * FROM blocked_apps") fun allApps(): LiveData<List<BlockedAppEntity>>
}

@Dao
interface TaskDao {
    @Insert suspend fun insert(t: TaskEntity): Long
    @Query("SELECT * FROM tasks ORDER BY dueAt") fun allTasks(): LiveData<List<TaskEntity>>
    @Query("UPDATE tasks SET done=:done WHERE id=:id") suspend fun setDone(id: Long, done: Boolean)
}

@Dao
interface ScheduleDao {
    @Insert suspend fun insert(s: ScheduleEntity): Long
    @Query("SELECT * FROM schedules") fun allSchedules(): LiveData<List<ScheduleEntity>>
    @Query("DELETE FROM schedules WHERE id=:id") suspend fun delete(id: Long)
}
