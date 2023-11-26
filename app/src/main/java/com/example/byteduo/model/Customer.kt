package com.example.byteduo.model

data class Customer(
    val cusFullName: String? = null,
    val cusEmail: String? = null,
    val cusPhoneNo: String? = null,
    val cusUsername: String? = null,
    val cusPassword: String? = null,
    val cusIsActive: Boolean = false
)