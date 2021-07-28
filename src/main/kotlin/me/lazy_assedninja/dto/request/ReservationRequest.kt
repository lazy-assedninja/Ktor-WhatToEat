package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ReservationRequest(
    val type: String?,
    val id: Int?,
)