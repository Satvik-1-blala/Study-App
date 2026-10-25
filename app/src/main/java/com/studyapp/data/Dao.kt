package com.studyapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SessionDao {
    @Insert fun insert(s: SessionEntity): Long
    @Query("SELECT * FROM sessions ORDER BY startedAt DESC") suspend fun all(): List<SessionEntity>
    @Query("SELECT COALESCE(SUM(durationSeconds),0) FROM sessions WHERE startedAt >= :since") suspend fun totalSince(since: Long): Int
}

@Dao
interface BlockedAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(b: BlockedAppEntity)
    @Query("DELETE FROM blocked_apps WHERE packageName=:pkg") suspend fun remove(pkg: String)
    @Query("SELECT * FROM blocked_apps") suspend fun all(): List<BlockedAppEntity>
}

@Dao
interface TaskDao {
    @Insert suspend fun insert(t: TaskEntity): Long
    @Query("SELECT * FROM tasks ORDER BY dueAt") suspend fun all(): List<TaskEntity>
    @Query("UPDATE tasks SET done=:done WHERE id=:id") suspend fun setDone(id: Long, done: Boolean)
}

@Dao
interface ScheduleDao {
    @Insert suspend fun insert(s: ScheduleEntity): Long
    @Query("SELECT * FROM schedules") suspend fun all(): List<ScheduleEntity>
    @Query("DELETE FROM schedules WHERE id=:id") suspend fun delete(id: Long)
}
