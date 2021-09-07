package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReservationDTO(

    val type: String?,
    val id: Int?,
)