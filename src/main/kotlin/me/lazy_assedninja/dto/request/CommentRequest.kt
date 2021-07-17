package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val userID: Int,
    val storeID: Int,

    val star: Float,
    val content: String
)
