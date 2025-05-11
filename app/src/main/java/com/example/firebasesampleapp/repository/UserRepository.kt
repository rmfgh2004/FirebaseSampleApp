package com.example.firebasesampleapp.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasesampleapp.model.User
import com.example.firebasesampleapp.ui.main.MainActivity
import com.example.firebasesampleapp.util.Names
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()

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

    fun findByUid(uid: String, onResult: (User?) -> Unit) {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val user = doc.toObject(User::class.java)
                onResult(user)
            }.addOnFailureListener {
                onResult(null)
            }
    }

    // user firestore
    fun insertUser(user: User, onSuccess: () -> Unit, onFailure: (exception: Throwable) -> Unit) {

        db.collection("users")
            .document(user.uid)
            .set(user)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateUserProfileImage(imageUri: Uri, user: User, onResult: (Boolean, String?) -> Unit) {
        val fileName = "${Names.USER_PROFILE_IMAGES_PATH}/${user.uid}.jpg"
        val imageRef = storage.reference.child(fileName)

        imageRef.putFile(imageUri)
            .continueWithTask { task ->
                imageRef.downloadUrl
            }.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                val newUser = user.copy(photoUrl = imageUrl)
                insertUser(
                    newUser,
                    onSuccess = {
                        onResult(true, imageUrl)
                    },
                    onFailure = {
                        onResult(false, null)
                    },
                )

            }.addOnFailureListener {
                onResult(false, null)
            }
    }

    companion object {
        private var instance: UserRepository? = null

        fun INSTANCE(): UserRepository {
            if (instance == null) {
                instance = UserRepository()
            }
            return instance!!
        }
    }
}