package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(

    val email: String?,
    val password: String?,
    val oldPassword: String?,
    val newPassword: String?,
    val headPortrait: String?,
    val verificationCode: String?
)