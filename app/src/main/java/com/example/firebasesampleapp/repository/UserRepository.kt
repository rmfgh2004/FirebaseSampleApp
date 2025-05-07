package com.example.firebasesampleapp.repository

import android.util.Log
import com.example.firebasesampleapp.ui.main.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserRepository {

    private val auth = FirebaseAuth.getInstance()

    fun signup(email: String, password: String) : Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String) : Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logout() {
        return auth.signOut()
    }

    fun getCurrentUser() : FirebaseUser? {
        return auth.currentUser
    }

}