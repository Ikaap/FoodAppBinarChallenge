package com.ikapurwanti.foodappbinarchallenge.presentation.feature.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private val binding : ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}