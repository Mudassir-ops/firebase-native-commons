package com.example.firebase_native_commons

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.firebase_core.domain.model.CrashReportAction
import com.example.firebase_core.domain.model.FirebaseEvent
import com.example.firebase_core.usecase.LogFirebaseCrashlyticsUseCase
import com.example.firebase_core.usecase.LogFirebaseEventUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var logEventUseCase: LogFirebaseEventUseCase

    @Inject
    lateinit var logFirebaseCrashlyticsUseCase: LogFirebaseCrashlyticsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // logBellClick()
        //testCrashlyticsUseCase()
    }

    private fun logBellClick() {
        lifecycleScope.launch {
            logEventUseCase(
                FirebaseEvent(
                    name = "home_bell_click",
                    params = mapOf("source" to "home_screen")
                )
            ).collect { result ->
                result.onSuccess {
                    Log.d("Analytics", "Event logged!")
                }.onFailure {
                    Log.e("Analytics", "Failed to log", it)
                }
            }
        }
    }

    private fun testCrashlyticsUseCase() {
        lifecycleScope.launch {
            logFirebaseCrashlyticsUseCase(
                CrashReportAction.LogMessage("User clicked test crash")
            ).collect { result ->
                result.onSuccess {
                    Log.d("Crashlytics", "Event logged!")
                }.onFailure {
                    Log.e("Crashlytics", "Failed to log", it)
                }
            }

            logFirebaseCrashlyticsUseCase(
                CrashReportAction.RecordException(IllegalStateException("Test exception"))
            ).collect { result ->
                result.onSuccess {
                    Log.d("Crashlytics", "Event logged!")
                }.onFailure {
                    Log.e("Crashlytics", "Failed to log", it)
                }
            }

            logFirebaseCrashlyticsUseCase(
                CrashReportAction.ForceCrash(FirebaseEvent("test_crash", mapOf("key" to "value")))
            ).collect { result ->
                result.onSuccess {
                    Log.d("Crashlytics", "Event logged!")
                }.onFailure {
                    Log.e("Crashlytics", "Failed to log", it)
                }
            }
        }
    }
}