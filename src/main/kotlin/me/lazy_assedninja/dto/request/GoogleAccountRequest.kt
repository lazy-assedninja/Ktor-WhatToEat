package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccountRequest(
    val googleID: String?
)