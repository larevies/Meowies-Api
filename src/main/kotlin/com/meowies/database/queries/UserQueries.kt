package com.meowies.database.queries

import com.meowies.database.connection.*
import com.meowies.modules.User
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class UserQueries {
    private var connection: Connection? = null

    init {
        try {
            connection = DriverManager.getConnection(url, username, DBPassword)

        } catch (e: SQLException) {
            println("${e.message}")
        }
    }


    /***
     * Поиск пользователя по имейлу
     */
    fun getUserByEmail(email:String): User? {

        var user: User? = null

        try {
            val statement = connection?.prepareStatement(
                """SELECT id, name, email, password, birthday, profilePicture FROM Users WHERE email = '${email}';"""
            )

            val resultSet = statement?.executeQuery()

            resultSet?.use {
                if (resultSet.next()) {
                    user = User(
                        Id = resultSet.getInt("id"),
                        Name = resultSet.getString("name"),
                        Email = resultSet.getString("email"),
                        Password = resultSet.getString("password"),
                        Birthday = resultSet.getString("birthday"),
                        ProfilePicture = resultSet.getInt("profilepicture")
                    )
                }
            }
            return user
        }
        catch (e: SQLException) {
            println("${e.message}")
            return user
        }
    }

    /***
     * Добавление пользователя в базу данных
     */
    fun addUser(name:String, email:String, password:String, birthday:String, profilePicture:Int): Boolean {
        val profPic: String = profilePicture.toString()
        try {
            val statement = connection?.createStatement()
            statement?.executeQuery(
                """INSERT INTO Users(
                        Name, Email, Password, Birthday, ProfilePicture)
                        VALUES ('$name', '$email', '$password', '$birthday', $profPic);
                    """
            )
            return true
        } catch (e: SQLException) {
            println("${e.message}")
            return e.message == "Запрос не вернул результатов."
        }
    }

    /***
     * Авторизация
     */
    fun authorization(email: String, password: String): User? {
        var user: User? = null
        try {
            val statement = connection?.prepareStatement(
                """SELECT id, name, email, password, birthday, profilePicture 
                    |FROM Users WHERE email = '${email}' AND password = '$password';""".trimMargin()
            )
            val resultSet = statement?.executeQuery()

            resultSet?.use {
                if (resultSet.next()) {
                    user = User(
                        Id = resultSet.getInt("id"),
                        Name = resultSet.getString("name"),
                        Email = resultSet.getString("email"),
                        Password = resultSet.getString("password"),
                        Birthday = resultSet.getString("birthday"),
                        ProfilePicture = resultSet.getInt("profilepicture")
                    )
                }
            }
            return user
        } catch (e: SQLException) {
            println("${e.message}")
            return user
        }
    }

    /***
     * Смена пароля
     */
    fun changePassword(email: String, password: String): Boolean {
        try {
            val statement = connection?.createStatement()
            statement?.executeQuery(
                """UPDATE Users
                    SET Password = '${password}'
                    WHERE Email = '${email}'
                    """
            )
            return true
        } catch (e: SQLException) {
            println("${e.message}")
            return e.message == "Запрос не вернул результатов."
        }
    }

    /***
     * Смена имени
     */
    fun changeName(email: String, name: String): Boolean {
        try {
            val statement = connection?.createStatement()
            statement?.executeQuery(
                """UPDATE Users
                    SET Name = '${name}'
                    WHERE Email = '${email}'
                    """
            )
            return true
        } catch (e: SQLException) {
            println("${e.message}")
            return e.message == "Запрос не вернул результатов."
        }
    }

    /***
     * Смена фото
     */
    fun changePicture(email: String, pic: String): Boolean {
        try {
            val statement = connection?.createStatement()
            statement?.executeQuery(
                """UPDATE Users
                    SET ProfilePicture = ${pic}
                    WHERE Email = '${email}'
                    """
            )
            return true
        } catch (e: SQLException) {
            println("${e.message}")
            return e.message == "Запрос не вернул результатов."
        }
    }
}