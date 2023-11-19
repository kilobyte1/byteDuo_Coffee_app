package com.example.byteduo.model

data class Admin(val adminId: Long, val adminUsername: String, val adminPassword: String,
val adminFullName: String, val adminEmail: String,val adminPhoneNumber: String, val adminIsActive: Boolean) {

    fun validateCredentials(username: String, password: String): Boolean {
        // alidate admin credentials
        return adminUsername == username && adminPassword == password
    }

}