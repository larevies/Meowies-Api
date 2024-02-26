package com.meowies.routes
import com.meowies.database.queries.UserQueries
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.receive
import com.meowies.modules.*

fun Route.userRouting() {

    val userQueries = UserQueries()

    route("/user") {

        /***
         * Поиск пользователя по имейлу
         */
        get("{email}") {
            val email = call.parameters["email"]
            if (email != null) {
                val user = userQueries.getUserByEmail(email)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respondText(
                        "User with email \"$email\" is not in the base",
                        status = HttpStatusCode.NotFound
                    )
                }
            }
        }

        /***
         * Добавление пользователя в базу данных
         */
        post {
            val user = call.receive<User>()
            val created = userQueries.addUser(
                user.Name,
                user.Email,
                user.Password,
                user.Birthday,
                user.ProfilePicture
            )
            if (created) {
                call.respondText("Success", status = HttpStatusCode.Created)
            } else {
                call.respondText("Fail", status = HttpStatusCode.Conflict)
            }
        }
    }

    /***
     * Авторизация
     */
    route("/login") {
        post {
            val intel = call.receive<Intel>()
            val user = userQueries.authorization(intel.Email, intel.Password)
            if (user != null) {
                return@post call.respond(user)
            } else {
                return@post call.respondText("Fail", status = HttpStatusCode.Conflict)
            }
        }
    }

    /***
     * Смена пароля
     */
    route("/change") {
        route("/password") {
            post {
                val intel = call.receive<Intel>()
                val res = userQueries.changePassword(intel.Email, intel.Password)
                if (res) {
                    return@post call.respondText("Success", status = HttpStatusCode.Accepted)
                } else {
                    return@post call.respondText("Fail", status = HttpStatusCode.NotFound)
                }
            }
        }

        /***
         * Смена имени
         */
        route("/name") {
            post {
                val name = call.receive<Name>()
                val res = userQueries.changeName(name.Email, name.Name)
                if (res) {
                    return@post call.respondText("Success", status = HttpStatusCode.Accepted)
                } else {
                    return@post call.respondText("Fail", status = HttpStatusCode.NotFound)
                }
            }
        }

        /***
         * Смена фото
         */
        route("/pic") {
            post {
                val pic = call.receive<Picture>()
                val res = userQueries.changePicture(pic.Email, pic.PicNum)
                if (res) {
                    return@post call.respondText("Success", status = HttpStatusCode.Accepted)
                } else {
                    return@post call.respondText("Fail", status = HttpStatusCode.NotFound)
                }
            }
        }

        route("/email") {
            post {
                val newEmail = call.receive<NewEmail>()
                val res = userQueries.changeEmail(newEmail.Email, newEmail.NewEmail)
                if (res) {
                    return@post call.respondText("Success", status = HttpStatusCode.Accepted)
                } else {
                    return@post call.respondText("Fail", status = HttpStatusCode.NotFound)
                }
            }
        }
    }
}