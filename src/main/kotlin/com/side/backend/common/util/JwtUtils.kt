package com.side.backend.common.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtUtils(@Value("\${config.secretKey}") private val secretKey: String) {
    @PostConstruct
    private fun createSignKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateTokens(userId: UUID): Array<String> {
        val accessToken = Jwts.builder()
            .subject(userId.toString())
            .issuedAt(Date())
            .expiration(Date((Date()).time + 3600000))
            .signWith(this.createSignKey())
            .compact()
        val refreshToken = Jwts.builder()
            .subject("refresh.$userId")
            .issuedAt(Date())
            .expiration(Date((Date()).time + 3600000))
            .signWith(this.createSignKey())
            .compact()
        return arrayOf(accessToken, refreshToken)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(this.createSignKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
