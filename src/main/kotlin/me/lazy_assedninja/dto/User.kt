package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val headPortrait: String?,
    val role: String,
    val verificationCode: String?,
    val createTime: String?,
    val updateTime: String?,

    val googleAccount: GoogleAccount?
)