package com.example.firebasesampleapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasesampleapp.repository.UserRepository
import com.google.firebase.auth.FirebaseAuthException

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val isSignIn = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()

    fun login(email: String, password: String) {
        if (!validationEmailAndPassword(email, password)) return

        userRepository.login(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isSignIn.value = true
            } else {
                isSignIn.value = false
                val error = (task.exception as? FirebaseAuthException)?.errorCode
                when (error) {
                    "ERROR_INVALID_EMAIL" -> emailError.value = "not greate email"
                    "ERROR_USER_NOT_FOUND" -> emailError.value = "email isn`t here"
                    "ERROR_WRONG_PASSWORD" -> passwordError.value = "wrong password"
                    else -> emailError.value = "i don`t know error"
                }
            }
        }
    }

    private fun validationEmailAndPassword(email: String, password: String) : Boolean {

        if (email.isBlank()) {
            emailError.value = "required input email"
            return false
        }

        if (password.isBlank()) {
            passwordError.value = "required input password"
            return false
        }

        return true
    }

}