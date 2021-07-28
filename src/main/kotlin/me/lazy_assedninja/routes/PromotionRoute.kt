package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.repository.PromotionRepository

fun Route.promotionRoute(promotionRepository: PromotionRepository = PromotionRepository()) {
    route("/Promotion") {
        get("GetPromotionList") {
            val promotions = promotionRepository.getAll()
            call.respond(promotions)
        }
    }
}