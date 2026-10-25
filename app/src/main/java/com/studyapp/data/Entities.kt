package com.studyapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_apps")
data class BlockedAppEntity(@PrimaryKey val packageName: String, val label: String, val profile: String = "default")

@Entity(tableName = "tasks")
data class TaskEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val title: String, val subject: String, val dueAt: Long, val done: Boolean = false)

@Entity(tableName = "schedules")
data class ScheduleEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val dayOfWeek: Int, val startMinute: Int, val endMinute: Int, val subject: String, val profile: String, val repeat: Boolean = true)
