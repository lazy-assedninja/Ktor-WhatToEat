package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class StoreDTO(

    val userID: Int?,
    val tagID: Int?,
    val keyword: String?
)