package me.lazy_assedninja.vo

import kotlinx.serialization.Serializable

@Serializable
data class Post(

    val id: Int,
    val title: String,
    val content: String,
    val createTime: String,

    val storeID: Int
)