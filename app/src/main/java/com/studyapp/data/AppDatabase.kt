package com.studyapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SessionEntity::class, BlockedAppEntity::class, TaskEntity::class, ScheduleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun taskDao(): TaskDao
    abstract fun scheduleDao(): ScheduleDao
}
