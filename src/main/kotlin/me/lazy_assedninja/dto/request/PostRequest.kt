package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val title: String,
    val content: String,

    val storeID: Int
)