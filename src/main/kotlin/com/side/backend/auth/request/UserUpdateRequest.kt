package com.side.backend.auth.request

data class UserUpdateRequest(
    val email: String,
    val phone: String,
    val nickname: String,
    val terms: Boolean,
)
