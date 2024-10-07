package com.side.backend.auth.repo

import com.side.backend.auth.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByPhone(phone: String?): List<User?>
}
