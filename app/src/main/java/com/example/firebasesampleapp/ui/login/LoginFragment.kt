package com.example.firebasesampleapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.FragmentLoginBinding
import com.example.firebasesampleapp.ui.main.MainActivity
import com.example.firebasesampleapp.ui.signup.SignUpFragment

class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider.create(this, LoginViewModelFactory())[LoginViewModel::class.java]
    }
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

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

        binding.buttonLoginLogin.setOnClickListener {

        }

        binding.buttonLoginSignup.setOnClickListener {
            mainActivity.replaceFragment(MainActivity.SIGNUP_FRAGMENT, false, null)
        }
    }

    private fun observe() {

    }

}