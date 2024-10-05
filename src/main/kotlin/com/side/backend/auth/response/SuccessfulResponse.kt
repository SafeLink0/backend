package com.side.backend.auth.response

data class SuccessfulResponse(
    val successful: Boolean = true,
    val message: String = "요청에 성공했어요"
)
