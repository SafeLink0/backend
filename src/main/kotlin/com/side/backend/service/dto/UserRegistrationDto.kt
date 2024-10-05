package com.side.backend.service.dto


import com.side.backend.domain.User


data class UserRegistrationDto(
    val email: String,
    val phone: String,
    val role: String,
    val nickname: String,
    val terms: Boolean,
) {
    companion object {
        fun from(entity: User): UserRegistrationDto =
            UserRegistrationDto(
                email = entity.email,
                phone = entity.phone,
                role = entity.role,
                nickname = entity.nickname,
                terms = entity.terms,
            )
    }
}
