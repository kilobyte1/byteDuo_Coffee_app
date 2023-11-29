package com.example.byteduo.model

data class Admin(
    val adminFullName: String,
    val adminEmail: String,
    val adminPhoneNo: String,
    val adminUsername: String,
    val adminPassword: String,
    val adminActive: Boolean
)