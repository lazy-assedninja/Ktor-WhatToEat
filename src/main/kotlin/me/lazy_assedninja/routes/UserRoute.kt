package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.dto.GoogleAccount
import me.lazy_assedninja.dto.Response
import me.lazy_assedninja.dto.User
import me.lazy_assedninja.dto.request.GoogleAccountRequest
import me.lazy_assedninja.dto.request.UserRequest
import me.lazy_assedninja.repository.GoogleAccountRepository
import me.lazy_assedninja.repository.UserRepository

fun Route.userRoute(
    userRepository: UserRepository = UserRepository(),
    googleAccountRepository: GoogleAccountRepository = GoogleAccountRepository()
) {
    route("/User") {
        post("SignUp") {
            val data = call.receive<User>()
            val checkIfExist = userRepository.getUser(data.email) == null
            if (checkIfExist) {
                userRepository.insert(data)
                call.respond(mapOf("result" to "1"))
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Email address already exist."))
            }
        }

        post("BindGoogleAccount") {
            val data = call.receive<GoogleAccount>()
            val googleID = data.googleID
            val checkIfExist = googleAccountRepository.get(googleID)
            if (checkIfExist != null)
                call.respond(mapOf("result" to "0", "errorMessage" to "Already been set to another account."))

            val userID = data.userID
            if (userID != null) {
                val user = userRepository.getUser(userID)
                if (user != null) {
                    googleAccountRepository.bind(user.id, data)
                    call.respond(mapOf("result" to "1"))
                } else {
                    call.respond(mapOf("result" to "0", "errorMessage" to "User Not Found."))
                }
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("Login") {
            val data = call.receive<UserRequest>()
            val email = data.email
            val password = data.password
            if (email != null && password != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    if (user.password == password) {
                        call.respond(Response(result = 1, body = user))
                    } else {
                        call.respond(mapOf("result" to "0", "errorMessage" to "Password wrong."))
                    }
                } else {
                    call.respond(mapOf("result" to "0", "errorMessage" to "User Not Found."))
                }
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("GoogleLogin") {
            val data = call.receive<GoogleAccountRequest>()
            val googleID = data.googleID
            if (googleID != null) {
                val user = userRepository.getUserByGoogleAccountID(googleID)
                if (user != null) {
                    call.respond(Response(result = 1, body = user))
                } else {
                    call.respond(mapOf("result" to "0", "errorMessage" to "User Not Found."))
                }
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("UpdatePassword") {
            val data = call.receive<UserRequest>()
            val email = data.email
            val oldPassword = data.oldPassword
            val newPassword = data.newPassword
            if (email != null && oldPassword != null && newPassword != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    if (user.password == oldPassword) {
                        user.password = newPassword
                        userRepository.update(user)
                        call.respond(mapOf("result" to "1"))
                    } else {
                        call.respond(mapOf("result" to "0", "errorMessage" to "Password wrong."))
                    }
                } else {
                    call.respond(mapOf("result" to "0", "errorMessage" to "User Not Found."))
                }
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("SendVerificationEmail") {
            val data = call.receive<UserRequest>()
            val email = data.email
            if (email != null) {
                // ...
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("ResetPassword") {
            val data = call.receive<UserRequest>()
            val email = data.email
            val verificationCode = data.verificationCode
            val newPassword = data.newPassword
            if (email != null && verificationCode != null && newPassword != null) {
                // ...
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("UpdatePermissionDeadline") {
            val data = call.receive<UserRequest>()
            val email = data.email
            if (email != null) {
                // ...
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("UpdateHeadPortrait") {
            val data = call.receive<UserRequest>()
            val email = data.email
            val picturePath = data.headPortrait
            if (email != null && picturePath != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    user.headPortrait = picturePath
                    userRepository.update(user)
                    call.respond(mapOf("result" to "1"))
                } else {
                    call.respond(mapOf("result" to "0", "errorMessage" to "User Not Found."))
                }
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }

        post("GetPicture") {
            val data = call.receive<UserRequest>()
            val email = data.email
            if (email != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    call.respond(
                        mapOf(
                            "result" to "1",
                            "headPortrait" to "https://${call.request.local.host}:${call.request.local.port}/${user.headPortrait}"
                        )
                    )
                } else {
                    call.respond(mapOf("result" to "0", "errorMessage" to "User Not Found."))
                }
            } else {
                call.respond(mapOf("result" to "0", "errorMessage" to "Data can't be empty."))
            }
        }
    }
}
