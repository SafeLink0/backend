package com.side.backend.api

import com.side.backend.api.request.UserRegistrationRequest
import com.side.backend.api.request.UserUpdateRequest
import com.side.backend.api.response.UserRegistrationResponse
import com.side.backend.api.response.UserResponse
import com.side.backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/customers")
    fun register(
        @RequestBody request: UserRegistrationRequest,
    ): ResponseEntity<UserRegistrationResponse> {
        val customerRegistration = userService.register(request)
        return ResponseEntity.ok().body(UserRegistrationResponse.from(customerRegistration))
    }

    @GetMapping("/customers/{id}")
    fun retrieve(
        @PathVariable id: UUID,
    ): ResponseEntity<UserResponse> = ResponseEntity.ok().body(UserResponse.from(userService.retrieve(id)))

    @PutMapping("/customers/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: UserUpdateRequest,
    ): ResponseEntity<UserResponse> =
        with(userService.update(id, request)) {
            return ResponseEntity.ok().body(UserResponse.from(this))
        }
}
