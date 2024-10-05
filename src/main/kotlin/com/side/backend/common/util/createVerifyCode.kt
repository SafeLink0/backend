package com.side.backend.common.util

fun createVerifyCode(): Int {
    return (10000..99999).random()
}
