package com.side.backend.service.dto

import java.util.UUID

data class UserDto(
    val userId: UUID,
    val email: String,
    val phone: String,
    val role: String,
    val nickname: String,
    val terms: Boolean,
) {
    companion object {
        fun from(entity: com.side.backend.domain.User): UserDto =
            UserDto(
                userId = entity.userId,
                email = entity.email,
                phone = entity.phone,
                role = entity.role,
                nickname = entity.nickname,
                terms = false,
            )
    }
}
