package com.example.tallerchallenge

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    fun validateUserCredentials(username: String, password: String) : Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }

    fun authenticateUserCredentials(username: String, password: String) : Boolean {
        return username == "admin" && password == "password"
    }

}