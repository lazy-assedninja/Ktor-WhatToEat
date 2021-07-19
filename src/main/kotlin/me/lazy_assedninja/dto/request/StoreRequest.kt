package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class StoreRequest(
    val userID: Int?,
    val tagID: Int?,
    val keyword: String?
)