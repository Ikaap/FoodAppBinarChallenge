package com.ikapurwanti.foodappbinarchallenge.presentation.feature.editprofile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityEditProfileBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.main.MainActivity
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModels {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(EditProfileViewModel(repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupForm()
        showUserData()
        setClickListeners()
        observeData()
    }

    private fun setupForm() {
        binding.layoutUserForm.tilName.isVisible = true
        binding.layoutUserForm.tilEmail.isVisible = true
        binding.layoutUserForm.tilEmail.isEnabled = false
        binding.layoutUserForm.tilPhoneNumber.isVisible = true
        binding.layoutUserForm.tilPhoneNumber.isEnabled = false
    }

    private fun showUserData() {
        binding.layoutUserForm.etName.setText(viewModel.getCurrentUser()?.fullName)
        binding.layoutUserForm.etEmail.setText(viewModel.getCurrentUser()?.email)
        binding.layoutUserForm.etPhoneNumber.setText(getString(R.string.text_089503250312))
    }

    private fun setClickListeners() {
        binding.btnChangeProfile.setOnClickListener {
            changeProfileData()
            navigateToMain()
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()

        }
        binding.tvChangePassword.setOnClickListener {
            requestChangePassword()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun changeProfileData() {
        if (checkNameValidation()) {
            val name = binding.layoutUserForm.etName.text.toString().trim()
            viewModel.changeProfile(name)
        }
    }

    private fun checkNameValidation(): Boolean {
        return if (binding.layoutUserForm.etName.text?.isEmpty() == true) {
            binding.layoutUserForm.tilName.isErrorEnabled = true
            binding.layoutUserForm.tilName.error = getString(R.string.text_error_name_cannot_empy)
            false
        } else {
            binding.layoutUserForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun requestChangePassword() {
        viewModel.createChangePasswordRequest()
        AlertDialog.Builder(this)
            .setMessage(
                "Change password request sent to your email" +
                        "${viewModel.getCurrentUser()?.email}"
            )
            .setPositiveButton("Okay") { _, _ ->
            }.create().show()
    }

    private fun observeData() {
        viewModel.changeProfileResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    binding.btnChangeProfile.isEnabled = true
                    showUserData()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnChangeProfile.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    binding.btnChangeProfile.isEnabled = true
                    Toast.makeText(
                        this,
                        "Change Profile Failed : ${it.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}