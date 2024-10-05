package com.side.backend.api.response


import com.side.backend.service.dto.UserDto


data class UserResponse(
    val email: String,
    val phone: String,
    val role: String,
    val nickname: String,
    val terms: Boolean,
) {
    companion object {
        fun from(dto: UserDto): UserResponse =
            UserResponse(
                email = dto.email,
                phone = dto.phone,
                role = dto.role,
                nickname = dto.nickname,
                terms = dto.terms
            )
    }
}
