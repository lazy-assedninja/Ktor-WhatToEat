package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class StoreRequest(val tagID: Int?, val keyword: String?, val userID: Int?)