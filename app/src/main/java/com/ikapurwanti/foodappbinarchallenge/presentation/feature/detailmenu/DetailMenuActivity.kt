package com.ikapurwanti.foodappbinarchallenge.presentation.feature.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantApiDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.service.RestaurantService
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityDetailMenuBinding
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import com.ikapurwanti.foodappbinarchallenge.utils.toCurrencyFormat

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val chuckerInterceptor = ChuckerInterceptor(this.applicationContext)
        val service = RestaurantService.invoke(chuckerInterceptor)
        val apiDataSource = RestaurantApiDataSource(service)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource)
        GenericViewModelFactory.create(DetailMenuViewModel(intent?.extras, repo))
    }

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
                    Toast.makeText(this, "Add to cart success !", Toast.LENGTH_SHORT).show()
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