package com.example.firebasesampleapp.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.FragmentSignUpBinding
import com.example.firebasesampleapp.ui.main.MainActivity

class SignUpFragment : Fragment() {

    private val binding: FragmentSignUpBinding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider.create(this, SignUpViewModelFactory())[SignUpViewModel::class.java]
    }
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextSignupId.setText("user01@naver.com")
        binding.editTextSignupPw.setText("user01!")
        setListener()
        observe()
    }

    private fun setListener() {
        binding.buttonSignupSignup.setOnClickListener {
            viewModel.signUp(
                binding.editTextSignupId.text.toString(),
                binding.editTextSignupPw.text.toString(),
            )
        }

        binding.buttonSignupCancel.setOnClickListener {
            mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
        }
    }

    private fun observe() {
        viewModel.isSignUp.observe(viewLifecycleOwner) { isSignUp ->
            if (isSignUp) {
                Toast.makeText(context, "success signUp", Toast.LENGTH_SHORT).show()
                mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
            } else {
                Toast.makeText(context, "fail signUp", Toast.LENGTH_SHORT).show()
            }
        }
    }
}