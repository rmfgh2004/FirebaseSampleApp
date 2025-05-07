package com.example.firebasesampleapp.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.ActivityMainBinding
import com.example.firebasesampleapp.ui.login.LoginFragment
import com.example.firebasesampleapp.ui.signup.SignUpFragment
import com.google.android.material.transition.MaterialSharedAxis

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        replaceFragment(LOGIN_FRAGMENT, false, null)
    }

    fun replaceFragment(name: String, addToBackStack: Boolean, bundle: Bundle?) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var newFragment : Fragment? = null
        var oldFragment : Fragment? = null

        if (newFragment != null) {
            oldFragment = newFragment
        }

        newFragment = when(name) {
            LOGIN_FRAGMENT -> LoginFragment()
            SIGNUP_FRAGMENT -> SignUpFragment()
            else -> Fragment()
        }

        newFragment.arguments = bundle

        if (oldFragment != null) {
            oldFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            oldFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
            oldFragment.enterTransition = null
            oldFragment.returnTransition = null
        }

        newFragment.exitTransition = null
        newFragment.reenterTransition = null
        newFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        newFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        fragmentTransaction.replace(R.id.fragmentContainerView_main, newFragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(name)
        }

        binding.textViewMainTitle.text = name
        fragmentTransaction.commit()
    }

    companion object {
        const val TAG = "askask"
        const val LOGIN_FRAGMENT = "LoginFragment"
        const val SIGNUP_FRAGMENT = "SignUpFragment"
    }
}