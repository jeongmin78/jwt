package com.cos.jwt.config.jwt

import com.cos.jwt.config.auth.PrincipalDetails
import com.cos.jwt.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * 스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 있음
 * login 요청해서 username, password 전송하면 (post)
 * UsernamePasswordAuthenticationFilter 가 동작함
 */
class JwtAuthenticationFilter(authenticationManager: AuthenticationManager?) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        println("JwtAuthenticationFilter : 로그인 시도중")

        // 1. username, password 받아서
        try {
            val om = ObjectMapper()
            val user = om.readValue(request?.inputStream, User::class.java)

            println(user)
            println("===== ${user.username}, ${user.password}")
            val authenticationToken = UsernamePasswordAuthenticationToken(user.username, user.password)
            println("----- $authenticationToken")

            // PrincipalDetailsService의  loadUserByUsername() 함수가 실행됨
            val authentication = authenticationManager.authenticate(authenticationToken)

            // authentication 객체가 session영역에 저장됨 => 로그인이 되었다는 뜻
            val principalDetails = authentication.principal as PrincipalDetails
            println(principalDetails.username)

            // 유저네임패스워드 토큰 생성

            // 유저네임패스워드 토큰 생성


            return super.attemptAuthentication(request, response)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 2. 정상인지 로그인 시도. authenticationManager로 로그인 시도를 하면
        //      - PrincipalDetailsService가 호출되어 loadUserByUsername() 함수 실행됨
        // 3. PrinciaplDetails를 세션에 담고 (권한 관리를 위해서)
        // 4. JWT 토큰을 리턴해주면 됨

        return null
    }
}