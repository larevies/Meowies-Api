package com.meowies.database.queries

import com.meowies.database.connection.DBPassword
import com.meowies.database.connection.url
import com.meowies.database.connection.username
import com.meowies.modules.Bookmark
import com.meowies.modules.User
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

    fun findBookmark(bookmark:Bookmark): Int? {

        var id: Int? = null
        var foundBookmark: Bookmark? = null
        var movieid = bookmark.MovieId.toString();
        var userid = bookmark.UserId.toString();

        try {
            val statement = connection?.prepareStatement(
                """SELECT id, MovieId, UserId FROM Bookmarks WHERE MovieId = '${movieid}' AND UserId = '${userid}';"""
            )

            val resultSet = statement?.executeQuery()

            resultSet?.use {
                if (resultSet.next()) {
                     foundBookmark = Bookmark(
                        Id = resultSet.getInt("id"),
                        MovieId = resultSet.getInt("movieid"),
                        UserId = resultSet.getInt("userid")
                     )
                }
            }
            return foundBookmark?.Id
        }
        catch (e: SQLException) {
            println("${e.message}")
            return foundBookmark?.Id
        }
    }
}