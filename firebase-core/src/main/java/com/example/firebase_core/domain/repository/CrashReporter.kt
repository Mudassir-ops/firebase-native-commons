package com.example.firebase_core.domain.repository

import com.example.firebase_core.domain.model.FirebaseEvent
import kotlinx.coroutines.flow.Flow

interface CrashReporter {
    fun logMessage(message: String): Flow<Result<Unit>>
    fun recordException(throwable: Throwable): Flow<Result<Unit>>
    fun forceCrash(event: FirebaseEvent): Flow<Result<Unit>>
}
