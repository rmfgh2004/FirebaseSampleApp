package com.example.firebasesampleapp.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.FragmentMyPageBinding
import com.example.firebasesampleapp.ui.login.LoginViewModel

class MyPageFragment : Fragment() {

    private val binding: FragmentMyPageBinding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private val viewModel: MyPageViewModel by lazy {
        ViewModelProvider.create(this, MyPageViewModelFactory())[MyPageViewModel::class.java]
    }
    private val loginViewModel: LoginViewModel by activityViewModels()

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

    private fun setListener() {

    }

    private fun observe() {
        loginViewModel.user.observe(viewLifecycleOwner) { user ->

            binding.textViewMypageEmail.text = user.email
        }
    }

}