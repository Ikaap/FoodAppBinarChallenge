package com.ikapurwanti.foodappbinarchallenge.presentation.feature.editprofile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityEditProfileBinding
import com.ikapurwanti.foodappbinarchallenge.utils.AssetWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

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
        binding.layoutUserForm.etPhoneNumber.setText(assetWrapper.getString(R.string.text_089503250312))
    }

    private fun setClickListeners() {
        binding.btnChangeProfile.setOnClickListener {
            if (checkNameValidation()) {
                changeProfileData()
                finish()
                Toast.makeText(
                    this,
                    assetWrapper.getString(R.string.text_profile_update_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.tvChangePassword.setOnClickListener {
            requestChangePassword()
        }
    }

    private fun changeProfileData() {
        val name = binding.layoutUserForm.etName.text.toString().trim()
        viewModel.changeProfile(name)
    }

    private fun checkNameValidation(): Boolean {
        return if (binding.layoutUserForm.etName.text?.isEmpty() == true) {
            binding.layoutUserForm.tilName.isErrorEnabled = true
            binding.layoutUserForm.tilName.error =
                assetWrapper.getString(R.string.text_error_name_cannot_empy)
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
                assetWrapper.getString(R.string.text_change_pass_email) + viewModel.getCurrentUser()?.email
            )
            .setPositiveButton(assetWrapper.getString(R.string.text_okay)) { _, _ ->
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
                        assetWrapper.getString(R.string.text_change_profile_failed) + it.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}