package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccount(
    val result: String?,

    val id: Int,
    val googleID: String,
    val email: String,
    val name: String,
    val pictureURL: String?,
    val createTime: String?,
    val updateTime: String?
)
