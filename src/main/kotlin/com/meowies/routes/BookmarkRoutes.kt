package com.meowies.routes

import com.meowies.database.queries.BookmarkQueries
import com.meowies.modules.Bookmark
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bookmarkRouting() {

    val bookmarkQueries = BookmarkQueries()

    route("/bookmark") {

        /***
         * Поиск закладок по пользователю
         */
        get("{userid}") {
            val userId = call.parameters["userid"]
            val userIdInt = userId?.toInt()
            if (userIdInt != null) {
                val bookmarks = bookmarkQueries.getBookmarksByUser(userIdInt)
                if (bookmarks != null) {
                    call.respond(bookmarks)
                } else {
                    call.respondText("No bookmarks found for user with id $userId",
                        status = HttpStatusCode.NotFound)
                }
            }
        }

        /***
         * Добавление закладки в базу данных
         */
        post {
            val bookmark = call.receive<Bookmark>()
            val created = bookmarkQueries.addBookmark(
                bookmark.MovieId,
                bookmark.UserId
            )
            if (created) {
                call.respondText("Success", status = HttpStatusCode.Created)
            } else {
                call.respondText("Fail", status = HttpStatusCode.Conflict)
            }
        }

        /***
         * Удаление закладки из базы данных
         */
        delete("{bookmarkid}") {
            val bookmarkId = call.parameters["bookmarkid"]
            val bookmarkIdInt = bookmarkId?.toInt()
            if (bookmarkIdInt != null) {
                val deleted = bookmarkQueries.removeBookmark(bookmarkIdInt)
                if (deleted) {
                    call.respondText("Success", status = HttpStatusCode.Accepted)
                } else {
                    call.respondText("Fail", status = HttpStatusCode.Conflict)
                }
            }
        }

        route("/find") {
            post {
                val bookmark = call.receive<Bookmark>()
                val id: Int? = bookmarkQueries.findBookmark(bookmark)
                call.respond(id.toString())
            }
        }
    }
}