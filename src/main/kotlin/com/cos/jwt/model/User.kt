package com.cos.jwt.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var roles: String? = null, // USER, ADMIN
) {
    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    fun getRoleList(): List<String?> {
        if (this.roles?.length!! > 0) {
            return this.roles!!.split(",")
        }
        return listOf()
    }
}