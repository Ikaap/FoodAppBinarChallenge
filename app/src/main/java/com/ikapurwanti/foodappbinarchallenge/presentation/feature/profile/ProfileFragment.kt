package com.ikapurwanti.foodappbinarchallenge.presentation.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.FragmentProfileBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.editprofile.EditProfileActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.login.LoginActivity
import com.ikapurwanti.foodappbinarchallenge.utils.AssetWrapper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDataUser()
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        getProfileData()
    }

    private fun getProfileData() {
        viewModel.getProfileData()
        observeData()
    }

    private fun observeData() {
        viewModel.getProfileResult.observe(viewLifecycleOwner) {
            showDataUser()
            viewModel.getProfileData()
        }
    }

    private fun setClickListeners() {
        binding.ivEditProfile.setOnClickListener {
            navigateToEditProfile()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun navigateToEditProfile() {
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun doLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                assetWrapper.getString(R.string.text_logout_dialog)
            )
            .setPositiveButton(assetWrapper.getString(R.string.text_yes)) { _, _ ->
                viewModel.doLogout()
                navigateToLogin()
            }.setNegativeButton(assetWrapper.getString(R.string.text_no)) { _, _ ->
            }.create().show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun showDataUser() {
        binding.tvPersonalName.text = viewModel.getCurrentUser()?.fullName.toString()
        binding.tvPersonalEmail.text = viewModel.getCurrentUser()?.email.toString()
    }
}
