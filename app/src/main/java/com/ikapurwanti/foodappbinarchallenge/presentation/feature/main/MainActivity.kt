package com.ikapurwanti.foodappbinarchallenge.presentation.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.AppPreferenceDataSourceImpl
import com.ikapurwanti.foodappbinarchallenge.data.local.datastore.appDataStore
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityMainBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.HomeFragment
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.home.HomeViewModel
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.PreferenceDataStoreHelperImpl

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.bottomNavView.setupWithNavController(navController)
    }
}