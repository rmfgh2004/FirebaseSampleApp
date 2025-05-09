package com.example.firebasesampleapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasesampleapp.model.User
import com.example.firebasesampleapp.ui.main.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    // user auth
    fun signup(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logout() {
        return auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // user firestore
    fun insertUser(user: User, onSuccess: () -> Unit, onFailure: (exception: Throwable) -> Unit): LiveData<Result<String>> {

        val result = MutableLiveData<Result<String>>()

        db.collection("users")
            .document(user.uid)
            .set(user)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }

        return result
    }

}