package com.side.backend.auth

import com.side.backend.auth.request.UserUpdateRequest
import com.side.backend.auth.request.UserRegistrationRequest
import com.side.backend.auth.domain.User
import com.side.backend.auth.dto.UserDto
import com.side.backend.auth.dto.UserRegistrationDto
import com.side.backend.common.util.Logger.Companion.log
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun register(request: UserRegistrationRequest): UserRegistrationDto {
        val customer =
            User(
                email = request.email,
                phone = request.phone,
                role = request.role,
            )
        return UserRegistrationDto.from(userRepository.save(customer))
            .also { log.info { "Customer Registration, id: ${customer.email}" } }
    }

    @Transactional
    fun update(
        id: UUID,
        request: UserUpdateRequest,
    ): UserDto {
        val customer = userRepository.findByIdOrNull(id) ?: throw RuntimeException()
        customer.update(request)

        return UserDto.from(customer)
    }

    fun retrieve(id: UUID): UserDto {
        val customer = userRepository.findByIdOrNull(id) ?: throw RuntimeException()
        return UserDto.from(customer)
    }

}
