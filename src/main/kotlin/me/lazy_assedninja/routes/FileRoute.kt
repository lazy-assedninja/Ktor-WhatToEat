package me.lazy_assedninja.routes

import io.ktor.http.content.*
import io.ktor.routing.*
import java.io.File

fun Route.fileRoute() {
    route("/file") {
        staticRootFolder = File("static")

        static("getTest") {
            files("image/store/77_xiang.jpg")
        }
    }
}