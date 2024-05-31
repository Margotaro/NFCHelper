package com.example.nfceditor.model

import kotlinx.serialization.Serializable

@Serializable
class EmailAddress(private val email: String) {

    init {
        require(isValidEmail(email)) { "Invalid email address" }
    }

    fun getEmail(): String {
        return email
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }

    override fun toString(): String {
        return email
    }
}