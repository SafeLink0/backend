package com.side.backend.auth.request

data class UserRegistrationRequest(
    val email: String,
    val phone: String,
    val role: String,
    val nickname: String,
    val terms: Boolean,
    val code: Int
)
