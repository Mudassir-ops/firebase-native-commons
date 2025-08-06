package com.example.firebase_native_commons

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FirebaseCore : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}