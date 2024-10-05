package com.side.backend.auth

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import com.side.backend.auth.domain.User
import com.side.backend.auth.domain.Verify
import com.side.backend.auth.dto.UserDto
import com.side.backend.auth.dto.UserRegistrationDto
import com.side.backend.auth.request.UserRegistrationRequest
import com.side.backend.auth.request.UserUpdateRequest
import com.side.backend.common.util.Logger.Companion.log
import com.side.backend.common.util.createVerifyCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val verifyRepository: VerifyRepository,
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

     suspend fun smsVerify(phone: String) {
        val code: Int = createVerifyCode()
        val result = Verify(code= code, phone = phone)
        withContext(Dispatchers.IO) {
            verifyRepository.save(result)
        }

        val request = PublishRequest {
            message = "[서비스명] 인증번호는 $code 입니다."
            phoneNumber = phone
        }

        SnsClient { region = "ap-northeast-1"

        }.use { snsClient ->
            snsClient.publish(request)
        }
    }
}
