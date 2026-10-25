package com.studyapp.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

data class InstalledApp(val packageName: String, val label: String, val isSystem: Boolean)

object AppEnumerator {
    fun list(context: Context): List<InstalledApp> {
        val pm = context.packageManager
        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .map { ai ->
                InstalledApp(
                    packageName = ai.packageName,
                    label = pm.getApplicationLabel(ai).toString(),
                    isSystem = (ai.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                )
            }
            .sortedBy { it.label.lowercase() }
    }
}
