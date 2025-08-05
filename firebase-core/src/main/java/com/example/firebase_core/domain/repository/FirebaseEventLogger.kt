package com.example.firebase_core.domain.repository

import com.example.firebase_core.domain.model.FirebaseEvent
import kotlinx.coroutines.flow.Flow

interface FirebaseEventLogger {
    fun logEvent(event: FirebaseEvent): Flow<Result<Unit>>

}