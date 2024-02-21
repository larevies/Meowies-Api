package com.meowies.modules

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val Id: Int? = null,
    val Name: String,
    val Email: String,
    val Password: String,
    val Birthday: String,
    val ProfilePicture: String
)
