package com.example.firebase_core.data.remote

import com.example.firebase_core.data.mapper.toData
import com.example.firebase_core.domain.model.FirebaseEvent
import com.example.firebase_core.domain.repository.FirebaseEventLogger
import com.example.firebase_core.source.FirebaseAnalyticsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FirebaseEventLoggerImpl @Inject constructor(
    private val dataSource: FirebaseAnalyticsDataSource
) : FirebaseEventLogger {

    override fun logEvent(event: FirebaseEvent): Flow<Result<Unit>> = flow {
        try {
            val eventData = event.toData()
            dataSource.logEvent(eventData.name ?: "", eventData.params ?: emptyMap())
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

}