package com.meowies.modules

import kotlinx.serialization.Serializable

@Serializable
data class Bookmark(
    val Id: Int? = null,
    val MovieId: Int,
    val UserId: Int
)
