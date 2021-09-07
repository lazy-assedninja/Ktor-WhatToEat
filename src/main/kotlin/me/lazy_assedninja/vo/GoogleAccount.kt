package me.lazy_assedninja.vo

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccount(

    val id: Int,
    val googleID: String,
    val email: String,
    val name: String,
    val pictureURL: String?,
    val createTime: String?,
    val updateTime: String?,

    val userID: Int? = null
)