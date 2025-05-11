package com.example.firebasesampleapp.ui.mypage

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasesampleapp.databinding.FragmentMyPageBinding
import com.example.firebasesampleapp.model.User
import com.example.firebasesampleapp.repository.UserRepository

class MyPageViewModel : ViewModel() {

    private val userRepository = UserRepository.INSTANCE()

    val userUpdateEvent = MutableLiveData<Pair<Boolean, String?>>()

    fun updateProfileImage(user: User, imageUri: Uri) {
        userRepository.updateUserProfileImage(imageUri, user) { success, uri ->
            userUpdateEvent.postValue(Pair(success, uri))
        }
    }

}