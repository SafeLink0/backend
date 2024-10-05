package com.side.backend.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Verify(
    @Id
    val code: Int,
    @Column
    val phone: String,
) {
    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
}
