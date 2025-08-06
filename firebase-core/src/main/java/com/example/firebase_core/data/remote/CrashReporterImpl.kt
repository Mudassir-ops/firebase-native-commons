package com.example.firebase_core.data.remote

import com.example.firebase_core.domain.model.FirebaseEvent
import com.example.firebase_core.domain.repository.CrashReporter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CrashReporterImpl(
    private val crashlytics: FirebaseCrashlytics
) : CrashReporter {

    override fun logMessage(message: String): Flow<Result<Unit>> = flow {
        try {
            crashlytics.log(message)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun recordException(throwable: Throwable): Flow<Result<Unit>> = flow {
        try {
            crashlytics.recordException(throwable)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun forceCrash(event: FirebaseEvent): Flow<Result<Unit>> = flow {
        crashlytics.log("Crashing with event: ${event.name}")
        event.params.forEach { (key, value) ->
            crashlytics.setCustomKey(key, value.toString())
        }
        emit(Result.success(Unit))
        throw RuntimeException("Force crash triggered for event: ${event.name}")
    }
}

