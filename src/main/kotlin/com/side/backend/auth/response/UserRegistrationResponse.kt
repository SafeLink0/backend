package com.side.backend.auth.response

import com.side.backend.auth.dto.UserRegistrationDto


data class UserRegistrationResponse(
    val statusCode: Int,
    val message: String,
    val email: String
) {
    companion object {
        fun from(dto: UserRegistrationDto): UserRegistrationResponse =
            UserRegistrationResponse(
                email = dto.email,
                statusCode = 200,
                message = "가입에 성공했어요"
            )
    }
}
