package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.vo.GoogleAccount
import me.lazy_assedninja.vo.User
import me.lazy_assedninja.dto.GoogleAccountDTO
import me.lazy_assedninja.dto.UserDTO
import me.lazy_assedninja.repository.GoogleAccountRepository
import me.lazy_assedninja.repository.UserRepository
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

fun Route.userRoute(
    emailAccount: String,
    emailPassword: String,
    userRepository: UserRepository = UserRepository(),
    googleAccountRepository: GoogleAccountRepository = GoogleAccountRepository()
) {

    route("/User") {
        post("Register") {
            val data = call.receive<User>()
            val checkIfExist = userRepository.getUser(data.email) == null
            if (checkIfExist) {
                userRepository.insert(data)
                call.respond(mapOf("result" to "Success."))
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Email address already exist.")
            }
        }

        post("BindGoogleAccount") {
            val data = call.receive<GoogleAccount>()
            val googleID = data.googleID
            val checkIfExist = googleAccountRepository.get(googleID)
            if (checkIfExist != null)
                call.respond(HttpStatusCode.InternalServerError, "Already been set to another account.")

            val userID = data.userID
            if (userID != null) {
                val user = userRepository.getUser(userID)
                if (user != null) {
                    googleAccountRepository.bind(user.id, data)
                    call.respond(mapOf("result" to "Success."))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("Login") {
            val data = call.receive<UserDTO>()
            val email = data.email
            val password = data.password
            if (email != null && password != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    if (user.password == password) {
                        call.respond(user)
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Password wrong.")
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("GoogleLogin") {
            val data = call.receive<GoogleAccountDTO>()
            val googleID = data.googleID
            if (googleID != null) {
                val user = userRepository.getUserByGoogleAccountID(googleID)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("ResetPassword") {
            val data = call.receive<UserDTO>()
            val email = data.email
            val oldPassword = data.oldPassword
            val newPassword = data.newPassword
            if (email != null && oldPassword != null && newPassword != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    if (user.password == oldPassword) {
                        user.password = newPassword
                        userRepository.update(user)
                        call.respond(mapOf("result" to "Success."))
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Password wrong.")
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("SendVerificationCode") {
            val data = call.receive<UserDTO>()
            val email = data.email
            if (email != null) {
                val user = userRepository.getUser(email)
                val verificationCode = userRepository.getVerificationCode()
                if (user != null) {
                    SimpleEmail().also {
                        it.hostName = "smtp.gmail.com"
                        it.isSSLOnConnect = true
                        it.sslSmtpPort = "465"
                        it.subject = "WhatToEat Server"
                        it.setAuthenticator(DefaultAuthenticator(emailAccount, emailPassword))
                        it.setFrom(emailAccount)
                        it.setMsg("Your verification code is $verificationCode.")
                        it.addTo(email)
                        it.send()
                    }
                    user.verificationCode = verificationCode
                    userRepository.update(user)
                    call.respond(mapOf("result" to "Success."))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("ForgetPassword") {
            val data = call.receive<UserDTO>()
            val email = data.email
            val verificationCode = data.verificationCode
            val newPassword = data.newPassword
            if (email != null && verificationCode != null && newPassword != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    if (user.verificationCode == verificationCode) {
                        user.password = newPassword
                        userRepository.update(user)
                        call.respond(mapOf("result" to "Success."))
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Verification Code wrong.")
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("UpdatePermissionDeadline") {
            val data = call.receive<UserDTO>()
            val email = data.email
            if (email != null) {
                // ...
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("UpdateHeadPortrait") {
            val data = call.receive<UserDTO>()
            val email = data.email
            val picturePath = data.headPortrait
            if (email != null && picturePath != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    user.headPortrait = picturePath
                    userRepository.update(user)
                    call.respond(mapOf("result" to "Success."))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("GetHeadPortrait") {
            val data = call.receive<UserDTO>()
            val email = data.email
            if (email != null) {
                val user = userRepository.getUser(email)
                if (user != null) {
                    call.respond(
                        mapOf(
                            "headPortrait" to "https://${call.request.local.host}:${call.request.local.port}/${user.headPortrait}"
                        )
                    )
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User Not Found.")
                }
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }
    }
}