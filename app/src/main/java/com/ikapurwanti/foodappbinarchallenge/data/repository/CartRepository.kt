package com.ikapurwanti.foodappbinarchallenge.data.repository

import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDatabaseDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.CartEntity
import com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper.toCartEntity
import com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper.toCartMenuList
import com.ikapurwanti.foodappbinarchallenge.model.Cart
import com.ikapurwanti.foodappbinarchallenge.model.CartMenu
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceed
import com.ikapurwanti.foodappbinarchallenge.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException

interface CartRepository {
    fun getCartData(): Flow<ResultWrapper<Pair<List<CartMenu>, Double>>>
    suspend fun createCart(menu: Menu, totalQty: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource
) : CartRepository {
    override fun getCartData(): Flow<ResultWrapper<Pair<List<CartMenu>, Double>>> {
        return cartDataSource.getAllCarts().map {
            proceed {
                val cartList = it.toCartMenuList()
                val totalPrice = cartList.sumOf {
                    val qty = it.cart.itemQuantity
                    val pricePerItem = it.menu.price
                    qty * pricePerItem
                }
                Pair(cartList, totalPrice)
            }
        }.map {
            if (it.payload?.first?.isEmpty() == true)
                ResultWrapper.Empty(it.payload)
            else
                it
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun createCart(menu: Menu, totalQty: Int): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let { menuId ->
            proceedFlow {
                val affectedRow = cartDataSource.insertCart(
                    CartEntity(menuId = menuId, itemQuantity = totalQty)
                )
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Menu ID not found")))
        }
    }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(modifiedCart.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }

}