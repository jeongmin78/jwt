package com.cos.jwt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestApiController {

    @GetMapping("/home")
    fun home(): String {
        return "<h1>home</h1>"
    }

    @PostMapping("/token")
    fun token(): String {
        return "<h1>token</h1>"
    }

}