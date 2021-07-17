package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val star: Float,
    val content: String,

    val userID: Int,
    val storeID: Int
)
