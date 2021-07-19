package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val star: Float,
    val content: String,
    val createTime: String,

    val storeID: Int,
    val userID: Int,
    val userName: String?,
    val userPicture: String?
)