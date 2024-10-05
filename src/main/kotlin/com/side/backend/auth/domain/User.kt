package com.side.backend.auth.domain

import com.side.backend.auth.request.UserUpdateRequest
import jakarta.persistence.*
import java.util.UUID

@Entity
class User(
    @Column
    var email: String,
    @Column
    var phone: String,
    @Column
    var role: String
) {
    @Id
    @GeneratedValue(generator = "uuid")
    var userId: UUID = UUID.randomUUID()
    @Column
    var nickname: String = "귀여운 햄스터"
    @Column
    var terms: Boolean = false

    fun update(request: UserUpdateRequest) {
        email = request.email;
        phone = request.phone;
        nickname = request.nickname;
        terms = request.terms;
    }
}
