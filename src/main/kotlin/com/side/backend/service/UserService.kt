package com.side.backend.service

import com.side.backend.api.request.UserUpdateRequest
import com.side.backend.api.request.UserRegistrationRequest
import com.side.backend.domain.User
import com.side.backend.service.dto.UserDto
import com.side.backend.service.dto.UserRegistrationDto
import com.side.backend.util.Logger.Companion.log
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
