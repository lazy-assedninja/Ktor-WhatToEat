package me.lazy_assedninja.application

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import me.lazy_assedninja.db.DataBaseFactory
import me.lazy_assedninja.plugins.*
import me.lazy_assedninja.routes.userRoute

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }

    configureRouting()

    // Database Init
    val user = environment.config.property("ktor.database.user").getString()
    val password = environment.config.property("ktor.database.password").getString()
    val db = DataBaseFactory(user, password)
    db.init()

    routing {
        userRoute()
    }
}
