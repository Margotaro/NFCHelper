package com.example.nfceditor.model

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumber(private val phoneNumber: String) {

    init {
        require(isValidPhoneNumber(phoneNumber)) { "Invalid phone number" }
    }

    fun getPhoneNumber(): String {
        return phoneNumber
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberRegex = "^\\+?[0-9]{10,15}$".toRegex()
        return phoneNumber.matches(phoneNumberRegex)
    }

    override fun toString(): String {
        return phoneNumber
    }
}