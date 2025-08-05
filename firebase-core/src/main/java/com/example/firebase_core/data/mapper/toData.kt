package com.example.firebase_core.data.mapper

import com.example.firebase_core.data.FirebaseEventData
import com.example.firebase_core.domain.model.FirebaseEvent

fun FirebaseEvent.toData(): FirebaseEventData {
    return FirebaseEventData(name = name, params = params)
}

fun FirebaseEventData.toDomain(): FirebaseEvent {
    return FirebaseEvent(name = name ?: "", params = params ?: emptyMap())
}
