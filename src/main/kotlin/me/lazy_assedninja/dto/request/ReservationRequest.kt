package me.lazy_assedninja.dto.request

import kotlinx.serialization.Serializable
import me.lazy_assedninja.dto.Reservation

@Serializable
data class ReservationRequest(
    val type: String?,
    val id: Int?,
    val reservation: Reservation?,

    val userID: Int?,
    val storeID: Int?
)