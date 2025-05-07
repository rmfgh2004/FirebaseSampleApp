package com.example.firebasesampleapp.ui.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasesampleapp.repository.UserRepository
import com.example.firebasesampleapp.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignUpViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val isSignUp: MutableLiveData<Boolean> = MutableLiveData()
    val emailError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData();

    fun signUp(email: String, password: String) {

        if (!validationEmailAndPassword(email, password)) return

        userRepository.signup(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isSignUp.value = true
                val currentUser = userRepository.getCurrentUser()
                if (currentUser != null) {
                    Log.d(MainActivity.TAG, "email : ${currentUser.email}")
                }
            } else {
                isSignUp.value = false
                when (task.exception) {
                    is FirebaseAuthUserCollisionException -> {
                        emailError.value = "already signup email"
                    }
                    else -> {
                        emailError.value = "i don`t know error"
                    }
                }
            }
        }
    }

    private fun validationEmailAndPassword(email: String, password: String) : Boolean {

        if (email.isBlank()) {
            emailError.value = "required input password"
            return false
        }

        if (password.isBlank()) {
            passwordError.value = "required input password"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError.value = "not great email"
            return false
        }

        if (password.length < 6) {
            passwordError.value = "password`s min length is 6"
            return false
        }


        return true
    }

}