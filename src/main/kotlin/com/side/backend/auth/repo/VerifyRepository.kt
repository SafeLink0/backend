package com.side.backend.auth.repo

import com.side.backend.auth.domain.Verify
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerifyRepository : JpaRepository<Verify, Int> {

}
