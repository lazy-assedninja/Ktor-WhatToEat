package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.vo.Reservation
import me.lazy_assedninja.dto.ReservationDTO
import me.lazy_assedninja.repository.ReservationRepository

fun Route.reservationRoute(reservationRepository: ReservationRepository = ReservationRepository()) {

    route("/Reservation") {
        post("CreateReservation") {
            val data = call.receive<Reservation>()
            reservationRepository.insert(data)
            call.respond(mapOf("result" to "Success."))
        }

        post("GetReservationList") {
            val data = call.receive<ReservationDTO>()
            val type = data.type
            val id = data.id
            if (type != null && id != null) {
                val reservations = reservationRepository.getAll(type, id)
                call.respond(reservations)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("GetReservationInformation") { }

        post("CancelReservation") {
            val data = call.receive<ReservationDTO>()
            val id = data.id
            if (id != null) {
                reservationRepository.delete(id)
                call.respond(mapOf("result" to "Success."))
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }
    }
}