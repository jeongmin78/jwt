package com.cos.jwt.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MyFilter3 : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        /**
         * 토큰: id, pw가 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어 응답을 해준다.
         * 요청할 때 마다 header의 Authorization에 value값으로 토큰이 넘어오면
         * 해당 토큰이 내가 만든 토큰이 맞는지 검증만 하면 됨 (RSA, HS256)
         */
        if (req.method.equals("POST")) {
            println("POST 요청됨")
            val headerAuth = req.getHeader("Authorization")
            println(headerAuth)
            println("필터3")

            if (headerAuth.equals("yesjm")) {
                chain?.doFilter(req, res)
            } else {
                val out = res.writer
                out.println("인증안됨")
            }
        }
    }

}