package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val id: Int?,
    val email: String?,
    val password: String?,
    val oldPassword: String?,
    val newPassword: String?,
    val headPortrait: String?,
    val verificationCode: String?
)