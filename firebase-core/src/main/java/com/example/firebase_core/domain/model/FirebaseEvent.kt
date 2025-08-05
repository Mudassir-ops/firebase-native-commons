package com.example.firebase_core.domain.model

data class FirebaseEvent(
    val name: String,
    val params: Map<String, Any> = emptyMap()
)