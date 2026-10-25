package com.studyapp

import android.app.Application
import com.studyapp.data.DatabaseProvider

class StudyApp : Application() {
    val database by lazy { DatabaseProvider.get(this) }
}
