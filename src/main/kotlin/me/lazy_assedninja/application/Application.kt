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
    install(DefaultHeaders)
    install(StatusPages) {
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError, "${it.message}")
        }
    }

    configureRouting()

    // Database Init
    val user = environment.config.property("ktor.database.user").getString()
    val password = environment.config.property("ktor.database.password").getString()
    val db = DataBaseFactory(user, password)
    db.init()

    // Test Data
    db.createTestData()

    val emailAccount =  environment.config.property("ktor.email.account").getString()
    val emailPassword =  environment.config.property("ktor.email.password").getString()
    routing {
        userRoute(emailAccount = emailAccount, emailPassword = emailPassword)
        storeRoute()
        reservationRoute()
        promotionRoute()
        favoriteRoute()
        customServiceRoute()
        fileRoute()
    }
}