package com.ikapurwanti.foodappbinarchallenge.presentation.detailmenu

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityDetailMenuBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.main.MainActivity

class DetailMenuActivity : AppCompatActivity() {

    private val binding : ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showLocation()
        backToHome()
    }

    private fun backToHome() {
        binding.cvBtnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLocation() {
        binding.clLocation.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
            )
            startActivity(intent)
        }
    }
}