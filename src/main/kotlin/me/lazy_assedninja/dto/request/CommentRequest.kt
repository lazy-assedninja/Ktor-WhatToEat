package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val storeID: Int
)