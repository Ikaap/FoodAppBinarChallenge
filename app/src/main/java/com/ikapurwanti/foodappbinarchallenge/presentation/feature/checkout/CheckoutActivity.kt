package com.ikapurwanti.foodappbinarchallenge.presentation.feature.checkout

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityCheckoutBinding
import com.ikapurwanti.foodappbinarchallenge.databinding.LayoutDialogCheckoutSuccessBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.common.adapter.CartListAdapter
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.main.MainActivity
import com.ikapurwanti.foodappbinarchallenge.utils.AssetWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import com.ikapurwanti.foodappbinarchallenge.utils.toCurrencyFormat
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeData()
        setClickListener()
    }

    private fun observeData() {
        observeCartData()
        observeCheckoutResult()

    }

    private fun observeCartData() {
        viewModel.cartListOrder.observe(this) { it ->
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
                        binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
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
                    binding.layoutState.tvError.text = assetWrapper.getString(R.string.text_cart_list_empty)
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvOrderList.isVisible = false
                    it.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                }

            )
        }
    }
    private fun observeCheckoutResult() {
        viewModel.checkoutResult.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    showSuccessDialog()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, assetWrapper.getString(R.string.text_checkout_error), Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun showSuccessDialog() {
        val binding: LayoutDialogCheckoutSuccessBinding =
            LayoutDialogCheckoutSuccessBinding.inflate(layoutInflater)
        val dialogView = binding.root

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        binding.tvBackToHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            viewModel.deleteAllCart()
        }
        dialog.show()
    }
    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvOrder.setOnClickListener {
            viewModel.order()
        }
    }

}