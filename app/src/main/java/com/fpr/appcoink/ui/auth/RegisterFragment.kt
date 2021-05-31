package com.fpr.appcoink.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fpr.appcoink.R
import com.fpr.appcoink.data.remote.auth.AuthDataSource
import com.fpr.appcoink.databinding.FragmentRegisterBinding
import com.fpr.appcoink.domain.auth.AuthRepoImp
import com.fpr.appcoink.presentation.auth.AuthViewModel
import com.fpr.appcoink.presentation.auth.AuthViewModelFactory
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.core.hide
import com.fpr.appcoink.core.show

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding:FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImp(AuthDataSource()))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        getSignUpInfo()

    }


    private fun getSignUpInfo(){

        binding.btnSignUp.setOnClickListener {

            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            if(password != confirmPassword){
                binding.editTextConfirmPassword.error = "Password does not match"
                binding.editTextPassword.error = "Password does not match"

                return@setOnClickListener
            }

            if (validateUserData(
                    username,
                    email,
                    password,
                    confirmPassword
                )
            ) return@setOnClickListener
            createUser(email,password,username)
            Log.d("singUpData", "getSignUpInfo: $username $email $password $confirmPassword")
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email,password,username).observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignUp.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(requireContext(),"Error ${it.exception}",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun validateUserData(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Username is empty"
            return true
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "Username is empty"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Username is empty"
            return true
        }
        return false
    }
}