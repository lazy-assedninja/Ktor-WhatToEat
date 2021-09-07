package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun Route.fileRoute() {

    route("/File") {
        staticRootFolder = File("static")

        static("Store") {
            files("image/store")
        }

        post("Upload") {
            val multipartData = call.receiveMultipart()
            multipartData.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileName = part.originalFileName as String
                    val fileBytes = part.streamProvider().readBytes()
                    File("image/$fileName").writeBytes(fileBytes)
                }
            }
            call.respond(call.respond(mapOf("result" to "Success.")))
        }
    }
}