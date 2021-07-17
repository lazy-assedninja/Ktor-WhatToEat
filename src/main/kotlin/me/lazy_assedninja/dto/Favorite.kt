package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class Favorite(val id: Int, val userID: Int, val storeID: Int)