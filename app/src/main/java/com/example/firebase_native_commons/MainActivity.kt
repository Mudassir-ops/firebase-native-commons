package com.example.firebase_native_commons

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.firebase_core.domain.model.FirebaseEvent
import com.example.firebase_core.usecase.LogFirebaseEventUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var logEventUseCase: LogFirebaseEventUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        logBellClick()
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
}