package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.dto.Report
import me.lazy_assedninja.repository.CustomServiceRepository

fun Route.customServiceRoute(customServiceRepository: CustomServiceRepository = CustomServiceRepository()) {
    route("/CustomService") {
        post("CreateReport") {
            val data = call.receive<Report>()
            customServiceRepository.insertReport(data)
            call.respond(mapOf("result" to "1"))
        }

        get("GetReportList") {
            val reports = customServiceRepository.getAllReports()
            call.respond(reports)
        }
    }
}