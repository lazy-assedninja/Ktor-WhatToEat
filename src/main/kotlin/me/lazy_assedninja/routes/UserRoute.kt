package me.lazy_assedninja.routes

import me.lazy_assedninja.dto.ID
import me.lazy_assedninja.dto.User
import me.lazy_assedninja.repository.UserRepository
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute(userRepository: UserRepository = UserRepository()) {
    route("/user"){
        post("insert") {
            val user = call.receive<User>()
            userRepository.insert(user)
            call.respond(mapOf("result" to "1"))
        }

        post("get") {
            val id = call.receive<ID>().id
            val user = userRepository.get(id)
            user?.let {
                call.respond(user)
            }
        }
    }
}
