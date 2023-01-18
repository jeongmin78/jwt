package com.cos.jwt.config

import com.cos.jwt.config.jwt.JwtAuthenticationFilter
import com.cos.jwt.filter.MyFilter3
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.context.SecurityContextHolderFilter
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val corsFilter: CorsFilter) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .addFilterAfter(MyFilter3(), SecurityContextHolderFilter::class.java)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // STATELESS: 세션을 사용하지 않는 방식
            .and()
            .addFilter(corsFilter) // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
            .authorizeRequests { authz ->
                authz
                    .antMatchers("/api/v1/user/**")
                    .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/api/v1/manager/**")
                    .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/api/v1/admin/**")
                    .access("hasRole('ROLE_ADMIN')")
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic().disable()
                    .formLogin().disable()
            }
            .addFilter(JwtAuthenticationFilter())
            .cors().and()
            .csrf().disable()

        return http.build()
    }

}