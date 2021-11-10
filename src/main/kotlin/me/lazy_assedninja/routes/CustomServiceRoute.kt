package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.vo.Report
import me.lazy_assedninja.repository.CustomServiceRepository

/**
 * Define APIs that related to custom service.
 */
fun Route.customServiceRoute(customServiceRepository: CustomServiceRepository = CustomServiceRepository()) {

    route("/CustomService") {
        post("CreateReport") {
            val data = call.receive<Report>()
            customServiceRepository.insertReport(data)
            call.respond(mapOf("result" to "Success."))
        }

        get("GetReportList") {
            val reports = customServiceRepository.getAllReports()
            call.respond(reports)
        }
    }
}