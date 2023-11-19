package com.example.byteduo.model

class Customer ( val cusFullName: String, val cusEmail: String,
                val cusPhoneNo: String, val cusUsername: String, val cusPassword: String, val cusIsActive: Boolean){
    //cust id will be auto gen by  the database

    fun validateCredentials(inputUsername: String, inputPassword: String): Boolean {
        return cusUsername == inputUsername && cusPassword == inputPassword
    }

}