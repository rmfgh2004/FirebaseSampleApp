package com.example.firebasesampleapp.model

data class User(
    val uid: String,
    val email: String,
    val photoUrl: String? = null,
)
