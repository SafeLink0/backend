package com.side.backend.auth

import com.side.backend.auth.request.CreateVerifyCodeRequest
import com.side.backend.auth.request.UserRegistrationRequest
import com.side.backend.auth.request.UserUpdateRequest
import com.side.backend.auth.response.SuccessfulResponse
import com.side.backend.auth.response.UserRegistrationResponse
import com.side.backend.auth.response.UserResponse
import com.side.backend.common.util.Logger.Companion.log
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

// userId 일단 path로 받는데, 나중엔 헤더 쿠키에서 가져와야 해요.

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/users")
    fun register(
        @RequestBody request: UserRegistrationRequest,
    ): ResponseEntity<UserRegistrationResponse> {
        val customerRegistration = userService.createUser(request)
        return ResponseEntity.ok().body(UserRegistrationResponse.from(customerRegistration))
    }

    @GetMapping("/users/{id}")
    fun retrieve(
        @PathVariable id: UUID,
    ): ResponseEntity<UserResponse> = ResponseEntity.ok().body(UserResponse.from(userService.getUser(id)))

    @PutMapping("/users/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: UserUpdateRequest,
    ): ResponseEntity<UserResponse> =
        with(userService.updateUser(id, request)) {
            return ResponseEntity.ok().body(UserResponse.from(this))
        }

    @PostMapping("/users/verify")
    suspend fun verify(@RequestBody request: CreateVerifyCodeRequest): ResponseEntity<SuccessfulResponse> {
        userService.smsVerify(phone = request.phone)
        return ResponseEntity.ok().body(SuccessfulResponse())
    }

    @DeleteMapping("/users/{id}")
    fun delete(@PathVariable id: UUID): Boolean {
        userService.deleteUser(id)
        return true
    }
}
