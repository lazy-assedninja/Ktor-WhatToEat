package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAccountRequest(
    val googleID: String,
    val email: String,
    val name: String,
    val pictureURL: String?,

    val userID: Int
)

