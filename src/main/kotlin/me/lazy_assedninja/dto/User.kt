package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    var password: String,
    var name: String,
    var headPortrait: String,
    val role: String,
    var verificationCode: String?,
    val createTime: String?,
    var updateTime: String?,

    var googleAccount: GoogleAccount?
)