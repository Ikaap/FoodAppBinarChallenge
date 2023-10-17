package com.ikapurwanti.foodappbinarchallenge.presentation.feature.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityLoginBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.HomeFragment
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.main.MainActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.register.RegisterActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.register.RegisterViewModel
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.highLightWord
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels {
        val firebaseAuth = FirebaseAuth.getInstance()
        val datasource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo: UserRepository = UserRepositoryImpl(datasource)
        GenericViewModelFactory.create(LoginViewModel(repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupFrom()
        setClickListeners()
        observeResult()

    }

    private fun setupFrom() {
        binding.layoutUserForm.tilEmail.isVisible = true
        binding.layoutUserForm.tilPassword.isVisible = true
    }

    private fun setClickListeners() {
        binding.tvNavToRegister.highLightWord(getString(R.string.text_register_here)) {
            navigateToRegister()
        }

        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun observeResult() {
        viewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    binding.btnLogin.isEnabled = false
                    navigateToHome()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.layoutUserForm.etEmail.text.toString().trim()
            val password = binding.layoutUserForm.etPassword.text.toString().trim()
            viewModel.doLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.layoutUserForm.etEmail.text.toString().trim()
        val password = binding.layoutUserForm.etPassword.text.toString().trim()
        return checkEmailValidation(email) && checkPasswordValidation(password, binding.layoutUserForm.tilPassword)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutUserForm.tilEmail.isErrorEnabled = true
            binding.layoutUserForm.tilEmail.error = getString(R.string.text_error_email_cannot_empy)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutUserForm.tilEmail.isErrorEnabled = true
            binding.layoutUserForm.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.layoutUserForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(password: String, textInputLayout: TextInputLayout): Boolean {
        return if (password.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.text_error_password_cannot_empy)
            false
        } else if (password.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }


}