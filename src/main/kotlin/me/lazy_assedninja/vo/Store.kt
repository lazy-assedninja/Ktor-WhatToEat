package me.lazy_assedninja.vo

import kotlinx.serialization.Serializable

@Serializable
data class Store(

    val id: Int,
    val placeID: String,
    val name: String,
    val address: String,
    val phone: String,
    val picture: String? = null,
    val latitude: String,
    val longitude: String,
    val website: String? = null,
    val star: Float? = null,
    val createTime: String? = null,
    val updateTime: String? = null,

    val isFavorite: Boolean? = null,

    val tagID: Int? = null
)
