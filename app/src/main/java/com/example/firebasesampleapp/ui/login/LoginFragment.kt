package com.example.firebasesampleapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            viewModel.login(
                binding.editTextLoginId.text.toString(),
                binding.editTextLoginPw.text.toString(),
            )
        }

        binding.buttonLoginSignup.setOnClickListener {
            mainActivity.replaceFragment(MainActivity.SIGNUP_FRAGMENT, false, null)
        }
    }

    private fun observe() {
        viewModel.isSignIn.observe(viewLifecycleOwner) { isSignIn ->
            if (isSignIn) {
                Toast.makeText(context, "success login", Toast.LENGTH_SHORT).show()
                mainActivity.replaceFragment(MainActivity.HOME_FRAGMENT, false, null)
            }
            // replace Home
        }

        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            binding.textLayoutLoginId.error = error
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.textLayoutLoginPw.error = error
        }
    }

}