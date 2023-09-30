package com.ikapurwanti.foodappbinarchallenge.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityMainBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityMainBinding

    private val fragmentHome = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(fragmentHome)
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_dashboard, fragment)
            .commit()

    }
}