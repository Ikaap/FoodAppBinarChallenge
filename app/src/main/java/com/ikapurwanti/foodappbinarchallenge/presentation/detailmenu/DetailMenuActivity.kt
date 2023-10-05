package com.ikapurwanti.foodappbinarchallenge.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityDetailMenuBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.presentation.main.MainActivity
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        GenericViewModelFactory.create(DetailMenuViewModel(intent?.extras))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showDetailMenu(viewModel.menu)
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvMenuPriceCalculated.text = "IDR $it"
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvCalculateOrder.text = it.toString()
        }
    }

    private fun setOnClickListener() {
        binding.ivBackHome.setOnClickListener {
            onBackPressed()
        }
        binding.clLocation.setOnClickListener {
            showLocation()
        }
        binding.tvMinOrder.setOnClickListener {
            viewModel.minOrder()
        }
        binding.tvPlusOrder.setOnClickListener {
            viewModel.plusOrder()
        }
    }

    private fun showDetailMenu(menu: Menu?) {
        menu?.let { menu ->
            binding.menuImage.load(menu.image)
            binding.tvMenuName.text = menu.name
            binding.tvMenuPrice.text = "IDR ${menu.price}"
            binding.tvMenuDesc.text = menu.desc
        }
    }

    private fun showLocation() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
        startActivity(intent)
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}