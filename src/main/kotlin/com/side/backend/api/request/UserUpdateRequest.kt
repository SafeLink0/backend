package com.side.backend.api.request

data class UserUpdateRequest(
    val email: String,
    val phone: String,
    val nickname: String,
    val terms: Boolean,
)
