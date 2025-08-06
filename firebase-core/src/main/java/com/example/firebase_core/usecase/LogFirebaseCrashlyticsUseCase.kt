package com.example.firebase_core.usecase

import com.example.firebase_core.domain.model.CrashReportAction
import com.example.firebase_core.domain.model.FirebaseEvent
import com.example.firebase_core.domain.repository.CrashReporter
import com.example.firebase_core.domain.repository.FirebaseEventLogger
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogFirebaseCrashlyticsUseCase @Inject constructor(
    private val crashReporter: CrashReporter
) {
    operator fun invoke(action: CrashReportAction): Flow<Result<Unit>> {
        return when (action) {
            is CrashReportAction.LogMessage -> crashReporter.logMessage(action.message)
            is CrashReportAction.RecordException -> crashReporter.recordException(action.throwable)
            is CrashReportAction.ForceCrash -> crashReporter.forceCrash(action.event)
        }
    }
}
