package com.side.backend.auth.dto

import com.side.backend.auth.domain.User
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
        fun from(entity: User): UserDto =
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
