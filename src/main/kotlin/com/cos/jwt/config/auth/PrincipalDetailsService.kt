package com.cos.jwt.config.auth

import com.cos.jwt.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// http://localhost:8080/login 요청이 오는 경우 동작함
// spring-security 의 기본 로그인 요청 주소가 /login 이므로
@Service
class PrincipalDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        println("PrincipalDetailsService의 loadUserByUsername()")
        val userEntity = userRepository.findByUsername(username)
        if (userEntity != null) {
            return PrincipalDetails(userEntity)
        }
        return null
    }
}