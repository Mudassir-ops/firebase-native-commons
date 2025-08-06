package com.example.firebase_core.domain.model

sealed class CrashReportAction {
    data class LogMessage(val message: String) : CrashReportAction()
    data class RecordException(val throwable: Throwable) : CrashReportAction()
    data class ForceCrash(val event: FirebaseEvent) : CrashReportAction()
}