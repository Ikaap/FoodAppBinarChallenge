package com.ikapurwanti.foodappbinarchallenge.presentation.feature.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val binding : ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupFrom()
        setClickListeners()
        observeResult()
    }

    private fun setupFrom() {
        TODO("Not yet implemented")
    }

    private fun setClickListeners() {
        TODO("Not yet implemented")
    }

    private fun observeResult() {
        TODO("Not yet implemented")
    }

}