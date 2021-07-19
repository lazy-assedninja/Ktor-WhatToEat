package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    val id: Int,
    val name: String,
    val phone: String,
    val amount: String,
    val time: String,
    val createTime: String,
    val updateTime: String,

    val storeID: Int,
    val storeName: String? = null,
    val userID:Int,
    val userName: String? = null
)