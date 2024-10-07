package com.side.backend.auth.response

data class UserRegistrationResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(tokens: Array<String>): UserRegistrationResponse =
            UserRegistrationResponse(
                accessToken = tokens[0],
                refreshToken = tokens[1]
            )

    }
}
