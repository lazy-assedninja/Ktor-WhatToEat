package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccountRequest(
    val userID: Int,

    val googleID: String,
    val email: String,
    val name: String,
    val pictureURL: String?
)

