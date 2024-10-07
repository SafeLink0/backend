package com.side.backend.auth.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime
import java.util.UUID

@Entity
class RefreshToken(
    @Column(nullable = false, unique = true)
    val refreshToken: String,
    @Id
    val userId: UUID,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    var user: User? = null
    ) {
    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
    @Column
    val updatedAt: LocalDateTime = LocalDateTime.now()
}
