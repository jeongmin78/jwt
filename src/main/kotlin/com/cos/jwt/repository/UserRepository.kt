package com.cos.jwt.repository

import com.cos.jwt.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface  UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): User?
}