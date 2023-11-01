package com.ikapurwanti.foodappbinarchallenge.presentation.feature.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import coil.load
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityDetailMenuBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.AssetWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import com.ikapurwanti.foodappbinarchallenge.utils.toCurrencyFormat
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModel{
        parametersOf(intent.extras ?: bundleOf())
    }

    private val assetWrapper: AssetWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showDetailMenu(viewModel.menu)
        observeData()
        setClickListener()
    }


    private fun showDetailMenu(menu: Menu?) {
        menu?.let { menu ->
            binding.menuImage.load(menu.imageUrl)
            binding.tvMenuName.text = menu.name
            binding.tvMenuPrice.text = menu.price.toCurrencyFormat()
            binding.tvMenuDesc.text = menu.desc
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvMenuPriceCalculated.text = it.toCurrencyFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvCalculateOrder.text = it.toString()
        }
        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, assetWrapper.getString(R.string.text_add_cart_success), Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun setClickListener() {
        binding.ivBackHome.setOnClickListener {
            onBackPressed()
        }
        binding.clLocation.setOnClickListener {
            showLocation()
        }
        binding.tvMinOrder.setOnClickListener {
            viewModel.minusOrder()
        }
        binding.tvPlusOrder.setOnClickListener {
            viewModel.plusOrder()
        }
        binding.llAddToCart.setOnClickListener {
            viewModel.addToCart()
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