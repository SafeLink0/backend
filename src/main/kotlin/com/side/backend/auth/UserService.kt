package com.side.backend.auth

import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import com.side.backend.auth.domain.RefreshToken
import com.side.backend.auth.domain.User
import com.side.backend.auth.domain.Verify
import com.side.backend.auth.dto.UserDto
import com.side.backend.auth.repo.RefreshTokenRepository
import com.side.backend.auth.repo.UserRepository
import com.side.backend.auth.repo.VerifyRepository
import com.side.backend.auth.request.UserRegistrationRequest
import com.side.backend.auth.request.UserUpdateRequest
import com.side.backend.common.util.JwtUtils
import com.side.backend.common.util.createVerifyCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
@Service
class UserService(
    private val userRepository: UserRepository,
    private val verifyRepository: VerifyRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtils: JwtUtils,
) {

    fun createUser(request: UserRegistrationRequest): Array<String> {
        verifyRepository.findByIdOrNull(id = request.code) ?: throw RuntimeException()
        val customer =
            User(
                email = request.email,
                phone = request.phone,
                role = request.role,
            )
        val user = userRepository.save(customer)  // 중복 회원 예외 처리 필요
        val tokens = jwtUtils.generateTokens(user.userId)
        val saveRefreshTokenEntity = RefreshToken(
            refreshToken = tokens[1],
            userId = user.userId,
        )
        refreshTokenRepository.save(saveRefreshTokenEntity)
        return tokens
    }

    fun updateUser(
        id: UUID,
        request: UserUpdateRequest,
    ): UserDto {
        val customer = userRepository.findByIdOrNull(id) ?: throw RuntimeException()
        customer.update(request)
        return UserDto.from(customer)
    }

    fun getUser(id: UUID): UserDto {
        val customer = userRepository.findByIdOrNull(id) ?: throw RuntimeException()
        return UserDto.from(customer)
    }

    fun deleteUser(id: UUID): Boolean {
        userRepository.deleteById(id)
        return true
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
