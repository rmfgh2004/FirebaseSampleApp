package com.example.firebasesampleapp.ui.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasesampleapp.repository.UserRepository
import com.example.firebasesampleapp.ui.main.MainActivity

class SignUpViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val isSignUp: MutableLiveData<Boolean> = MutableLiveData()

    fun signUp(email: String, password: String) {
        userRepository.signup(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isSignUp.value = true
                val currentUser = userRepository.getCurrentUser()
                if (currentUser != null) {
                    Log.d(MainActivity.TAG, "email : ${currentUser.email}")
                }
            } else {
                isSignUp.value = false
            }
        }
    }

}