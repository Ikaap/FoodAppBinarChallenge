package com.ikapurwanti.foodappbinarchallenge.presentation.feature.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityCheckoutBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.common.adapter.CartListAdapter
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeData() {
        viewModel.cartListOrder.observe(this){ it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvOrderList.isVisible = true
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvOrderList.apply {
                        layoutManager = LinearLayoutManager(this@CheckoutActivity)
                        adapter = this@CheckoutActivity.adapter
                    }
                    it.payload?.let { (carts, totalPrice) ->
                        adapter.setData(carts)
                        binding.tvTotalPrice.text = "IDR $totalPrice"
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvOrderList.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvOrderList.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_list_empty)
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvOrderList.isVisible = false
                    it.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPrice.text = "IDR $totalPrice"
                    }
                }

            )
        }
    }
}