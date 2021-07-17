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

    val storeName: String? = null,
    val userName: String? = null
)
