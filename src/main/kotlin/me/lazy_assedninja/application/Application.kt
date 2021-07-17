package me.lazy_assedninja.application

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.routes.*
import me.lazy_assedninja.db.DataBaseFactory
import me.lazy_assedninja.plugins.configureRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }
    install(StatusPages) {
        status(HttpStatusCode.NotFound, HttpStatusCode.InternalServerError) {
            call.respond(mapOf("result" to "0", "message" to "${it.value} ${it.description}"))
        }
        exception<Throwable> {
            call.respond(mapOf("result" to "0", "message" to it.message))
            log.error("Throwable", it)
        }
    }

    configureRouting()

    // Database Init
    val user = environment.config.property("ktor.database.user").getString()
    val password = environment.config.property("ktor.database.password").getString()
    val db = DataBaseFactory(user, password)
    db.init()

    db.createTestData()

    routing {
        userRoute()
        storeRoute()
        reservationRoute()
    }
}