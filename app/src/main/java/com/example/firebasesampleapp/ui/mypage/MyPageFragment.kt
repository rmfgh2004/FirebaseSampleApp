package com.example.firebasesampleapp.ui.mypage

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.FragmentMyPageBinding
import com.example.firebasesampleapp.ui.login.LoginViewModel
import androidx.core.net.toUri
import com.example.firebasesampleapp.ui.main.MainActivity

class MyPageFragment : Fragment() {

    private val binding: FragmentMyPageBinding by lazy {
        FragmentMyPageBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: MyPageViewModel by lazy {
        ViewModelProvider.create(this, MyPageViewModelFactory())[MyPageViewModel::class.java]
    }
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    private val imagePermission =
        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_IMAGES else
            Manifest.permission.READ_EXTERNAL_STORAGE


    private val imagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGrant ->
        if (isGrant) {
            pickImageLauncher.launch("image/*")
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.updateProfileImage(loginViewModel.user.value!!, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        observe()
    }

    private fun imagePermissionCheck() {
        if (ActivityCompat.checkSelfPermission(mainActivity, imagePermission) == PackageManager.PERMISSION_GRANTED) {
            pickImageLauncher.launch("image/*")
        } else {
            imagePermissionLauncher.launch(imagePermission)
        }
    }

    private fun setListener() {
        binding.imageViewMypageProfile.setOnLongClickListener {
            imagePermissionCheck()
            true
        }
    }

    private fun observe() {
        loginViewModel.user.observe(viewLifecycleOwner) { user ->
            Log.d("askask", "user observe")
            if (user.photoUrl != null) {
                Glide.with(this)
                    .load(user.photoUrl.toUri())
                    .placeholder(R.drawable.placeholder_background)
                    .error(R.drawable.error_background)
                    .into(binding.imageViewMypageProfile)
            }

            binding.textViewMypageEmail.text = user.email
        }

        viewModel.userUpdateEvent.observe(viewLifecycleOwner) { (success, uri) ->
            if (success) {
                loginViewModel.updateUser(loginViewModel.user.value!!.copy(photoUrl = uri))
                Toast.makeText(mainActivity, "Success Update", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(mainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}