package com.meowies.database.connection
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/*
fun main() {
    val connectionDB = ConnectionDB()
    if (connectionDB.isDatabaseConnected()) {
        println(successMessage)
    } else {
        println(errorMessage)
    }
}*/

class ConnectionDB {

    private var connection: Connection? = null
    init {
        try {
            connection = DriverManager.getConnection(url, username, DBPassword)
            if (connection != null) {
                println(successMessage)
            }
        } catch (e: SQLException) {
            println(errorMessage)
            println("${e.message}")
        }

    }
    fun isDatabaseConnected(): Boolean {
        return connection != null
    }
}