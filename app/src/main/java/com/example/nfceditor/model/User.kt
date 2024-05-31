package com.example.nfceditor.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
@Serializable
class User(
    val name: String,
    val surname: String,
    val email: EmailAddress,
    val phone: PhoneNumber
) {
    fun serializeUser(user: User): String {
        return Json.encodeToString(user)
    }

    fun deserializeUser(jsonString: String): User {
        return Json.decodeFromString(jsonString)
    }
}



