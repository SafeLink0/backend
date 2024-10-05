package com.side.backend.auth.domain

import jakarta.persistence.*
import java.util.UUID

@Entity
class Terms(
    @Column
    var subject: String,
    @Column
    var version: Int,
    @Id
    var userId: UUID,
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @MapsId
    @JoinColumn(name = "userId")
    var user: User,
) {
    @Column
    var role: String = "부모"
    @Column
    var terms: Boolean = false
}
