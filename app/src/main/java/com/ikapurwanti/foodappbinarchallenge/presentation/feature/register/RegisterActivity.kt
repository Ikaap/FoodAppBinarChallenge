package com.ikapurwanti.foodappbinarchallenge.presentation.feature.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityRegisterBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.login.LoginActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.main.MainActivity
import com.ikapurwanti.foodappbinarchallenge.utils.AssetWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.highLightWord
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupFrom()
        setClickListeners()
        observeResult()
    }

    private fun setupFrom() {
        binding.layoutUserForm.tilName.isVisible = true
        binding.layoutUserForm.tilEmail.isVisible = true
        binding.layoutUserForm.tilPassword.isVisible = true
        binding.layoutUserForm.tilConfirmPassword.isVisible = true
    }

    private fun setClickListeners() {
        binding.tvLoginHere.highLightWord(assetWrapper.getString(R.string.text_highlight_login_here)) {
            navigateToLogin()
        }

        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.btnRegister.isEnabled = false
                    navigateToHome()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        assetWrapper.getString(R.string.text_register_failed) + it.exception?.message.orEmpty(),
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

    private fun doRegister() {
        if (isFormValid()) {
            val name = binding.layoutUserForm.etName.text.toString().trim()
            val email = binding.layoutUserForm.etEmail.text.toString().trim()
            val password = binding.layoutUserForm.etPassword.text.toString().trim()
            viewModel.doRegister(name, email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val name = binding.layoutUserForm.etName.text.toString().trim()
        val email = binding.layoutUserForm.etEmail.text.toString().trim()
        val password = binding.layoutUserForm.etPassword.text.toString().trim()
        val confirmPassword = binding.layoutUserForm.etConfirmPassword.text.toString().trim()

        return checkNameValidation(name) && checkEmailValidation(email) &&
            checkPasswordValidation(password, binding.layoutUserForm.tilPassword) &&
            checkPasswordValidation(
                confirmPassword,
                binding.layoutUserForm.tilConfirmPassword
            ) &&
            checkPasswordAndConfirmPassword(password, confirmPassword)
    }

    private fun checkNameValidation(name: String): Boolean {
        return if (name.isEmpty()) {
            binding.layoutUserForm.tilName.isErrorEnabled = true
            binding.layoutUserForm.tilName.error = assetWrapper.getString(R.string.text_error_name_cannot_empy)
            false
        } else {
            binding.layoutUserForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutUserForm.tilEmail.isErrorEnabled = true
            binding.layoutUserForm.tilEmail.error = assetWrapper.getString(R.string.text_error_email_cannot_empy)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutUserForm.tilEmail.isErrorEnabled = true
            binding.layoutUserForm.tilEmail.error = assetWrapper.getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.layoutUserForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        password: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (password.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = assetWrapper.getString(R.string.text_error_password_cannot_empy)
            false
        } else if (password.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = assetWrapper.getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordAndConfirmPassword(
        password: String,
        confirmPassword: String
    ): Boolean {
        return if (password != confirmPassword) {
            binding.layoutUserForm.tilPassword.isErrorEnabled = true
            binding.layoutUserForm.tilConfirmPassword.isErrorEnabled = true
            binding.layoutUserForm.tilPassword.error =
                assetWrapper.getString(R.string.text_error_password_does_not_match)
            binding.layoutUserForm.tilConfirmPassword.error =
                assetWrapper.getString(R.string.text_error_password_does_not_match)
            false
        } else {
            binding.layoutUserForm.tilPassword.isErrorEnabled = false
            binding.layoutUserForm.tilConfirmPassword.isErrorEnabled = false
            true
        }
    }
}
