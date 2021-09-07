package me.lazy_assedninja.vo

import kotlinx.serialization.Serializable

@Serializable
data class Favorite(

    val id: Int,

    val userID: Int,
    val storeID: Int
)