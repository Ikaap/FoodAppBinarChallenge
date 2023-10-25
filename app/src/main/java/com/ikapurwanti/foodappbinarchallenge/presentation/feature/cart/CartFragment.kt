package com.ikapurwanti.foodappbinarchallenge.presentation.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.data.local.database.AppDatabase
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantApiDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.service.RestaurantService
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepositoryImpl
import com.ikapurwanti.foodappbinarchallenge.databinding.FragmentCartBinding
import com.ikapurwanti.foodappbinarchallenge.di.AppInjection
import com.ikapurwanti.foodappbinarchallenge.model.Cart
import com.ikapurwanti.foodappbinarchallenge.presentation.common.adapter.CartListAdapter
import com.ikapurwanti.foodappbinarchallenge.presentation.common.adapter.CartListener
import com.ikapurwanti.foodappbinarchallenge.presentation.feature.checkout.CheckoutActivity
import com.ikapurwanti.foodappbinarchallenge.utils.GenericViewModelFactory
import com.ikapurwanti.foodappbinarchallenge.utils.proceedWhen
import com.ikapurwanti.foodappbinarchallenge.utils.toCurrencyFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels {
        AppInjection.getCartViewModel()
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.deleteCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setClickListener()
    }

    private fun observeData() {
        viewModel.cartList.observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.btnCheckout.isEnabled = true
                    binding.rvCartList.isVisible = true
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCartList.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = this@CartFragment.adapter
                    }
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.setData(carts)
                        binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCartList.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvCartList.isVisible = false
                },
                doOnEmpty = {
                    binding.btnCheckout.isEnabled = false
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_list_empty)
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvCartList.isVisible = false
                    it.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                    }
                }
            )
        }
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

}