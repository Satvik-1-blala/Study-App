package com.studyapp.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile private var instance: AppDatabase? = null
    fun get(context: Context): AppDatabase =
        instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "study.db").build().also { instance = it }
        }
}
