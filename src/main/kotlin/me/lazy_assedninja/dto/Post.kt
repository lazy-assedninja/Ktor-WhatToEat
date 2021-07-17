package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val createTime: String
)
