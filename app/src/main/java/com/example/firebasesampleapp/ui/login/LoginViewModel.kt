package com.example.firebasesampleapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasesampleapp.repository.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val isSignIn = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()

    fun login(email: String, password: String) {

    }

}