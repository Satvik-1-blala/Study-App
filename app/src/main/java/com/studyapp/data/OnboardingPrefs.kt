package com.studyapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("onboarding")

object OnboardingPrefs {
    private val GOAL = stringPreferencesKey("focus_goal")
    private val DAILY_HOURS = intPreferencesKey("daily_hours")
    private val DISTRACTIONS = stringPreferencesKey("distractions")

    fun flow(context: Context): Flow<Triple<String?, Int, Set<String>>> = context.dataStore.data.map { p ->
        Triple(p[GOAL], p[DAILY_HOURS] ?: 3, (p[DISTRACTIONS] ?: "").split(",").filter { it.isNotBlank() }.toSet())
    }

    suspend fun save(context: Context, goal: String?, dailyHours: Int, distractions: Set<String>) {
        context.dataStore.edit { p ->
            if (goal != null) p[GOAL] = goal
            p[DAILY_HOURS] = dailyHours
            p[DISTRACTIONS] = distractions.joinToString(",")
        }
    }
}
