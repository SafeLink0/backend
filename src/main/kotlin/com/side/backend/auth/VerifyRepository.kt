package com.side.backend.auth

import com.side.backend.auth.domain.Verify
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VerifyRepository : JpaRepository<Verify, UUID> {

}
