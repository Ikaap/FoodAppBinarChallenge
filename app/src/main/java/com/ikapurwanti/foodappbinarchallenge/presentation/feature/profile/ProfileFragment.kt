package com.ikapurwanti.foodappbinarchallenge.presentation.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.firebase.auth.datasource.FirebaseAuthDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.UserRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.FragmentProfileBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.editprofile.EditProfileActivity
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.login.LoginActivity
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        showDataUser()
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
                "Do you want to logout ?"
            )
            .setPositiveButton("Yes") { _, _ ->
                viewModel.doLogout()
                navigateToLogin()
            }.setNegativeButton("No") { _, _ ->

            }.create().show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun showDataUser() {
        binding.tvPersonalName.text = viewModel.getCurrentUser()?.fullName
        binding.tvPersonalEmail.text = viewModel.getCurrentUser()?.email
    }

}