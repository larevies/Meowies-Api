package com.meowies.database.queries

import com.meowies.database.connection.DBPassword
import com.meowies.database.connection.url
import com.meowies.database.connection.username
import com.meowies.modules.Bookmark
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class BookmarkQueries {

    private var connection: Connection? = null

    init {
        try {
            connection = DriverManager.getConnection(url, username, DBPassword)

        } catch (e: SQLException) {
            println("${e.message}")
        }
    }

    /***
     * Поиск закладок по пользователю
     */
    fun getBookmarksByUser(userId: Int): List<Bookmark>? {
        val bookmarks = mutableListOf<Bookmark>()
        return try {
            val statement = connection?.createStatement()
            val resultSet = statement?.executeQuery("""SELECT id, movieid, userid 
                |FROM bookmarks WHERE userid = ${userId} """.trimMargin())
            resultSet?.use {
                while (resultSet.next()) {
                    val bookmark = Bookmark(
                        Id = resultSet.getInt("id"),
                        MovieId = resultSet.getInt("movieid"),
                        UserId = resultSet.getInt("userid")
                    )
                    bookmarks.add(bookmark)
                }
            }
            bookmarks
        } catch (e: SQLException) {
            println("${e.message}")
            null
        }
    }

    /***
     * Добавление закладки в базу данных
     */
    fun addBookmark(movieId:Int, userId:Int): Boolean {
        try {
            val statement = connection?.createStatement()
            statement?.executeQuery(
                """INSERT INTO Bookmarks(
                        MovieId, UserId)
                        VALUES ('$movieId', '$userId');
                    """
            )
            return true
        } catch (e: SQLException) {
            println("${e.message}")
            return e.message == "Запрос не вернул результатов."
        }
    }

    /***
     * Удаление закладки из базы данных
     */
    fun removeBookmark(id:Int): Boolean {
        try {
            val statement = connection?.createStatement()
            statement?.executeQuery(
                """DELETE FROM Bookmarks
                        WHERE Id = ${id};
                    """
            )
            return true
        } catch (e: SQLException) {
            println("${e.message}")
            return e.message == "Запрос не вернул результатов."
        }
    }
}