package com.side.backend.auth.repo

import com.side.backend.auth.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
}
