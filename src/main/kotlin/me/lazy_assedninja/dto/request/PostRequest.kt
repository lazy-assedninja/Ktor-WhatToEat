package me.lazy_assedninja.dto.request

data class PostRequest(
    val storeID: Int,

    val title: String,
    val content: String
)