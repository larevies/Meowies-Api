package com.meowies.modules;

import kotlinx.serialization.Serializable;

@Serializable
data class Intel(
    val Email: String,
    val Password: String
)

@Serializable
data class Name(
    val Email: String,
    val Name: String
)
@Serializable
data class Picture(
    val Email: String,
    val PicNum: Int
)

@Serializable
data class NewEmail(
    val Email: String,
    val NewEmail: String
)

