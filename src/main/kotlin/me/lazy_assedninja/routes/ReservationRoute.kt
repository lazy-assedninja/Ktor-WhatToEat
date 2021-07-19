package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.dto.Reservation
import me.lazy_assedninja.dto.Response
import me.lazy_assedninja.dto.request.ReservationRequest
import me.lazy_assedninja.repository.ReservationRepository

fun Route.reservationRoute(reservationRepository: ReservationRepository = ReservationRepository()) {
    route("/Reservation") {
        post("CreateReservation") {
            val data = call.receive<Reservation>()
            reservationRepository.insert(data)
            call.respond(mapOf("result" to "1"))
        }

        post("GetReservationList") {
            val data = call.receive<ReservationRequest>()
            val type = data.type
            val id = data.id
            if (type != null && id != null) {
                val reservations = reservationRepository.getAll(type, id)
                call.respond(Response(result = 1, body = reservations))
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("GetReservationInformation") { }

        post("CancelReservation") {
            val data = call.receive<ReservationRequest>()
            val id = data.id
            if (id != null) {
                reservationRepository.delete(id)
                call.respond(mapOf("result" to "1"))
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }
    }
}