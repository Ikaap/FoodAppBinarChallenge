package com.ikapurwanti.foodappbinarchallenge.data.repository

import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.CartEntity
import com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper.toCartEntity
import com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper.toCartList
import com.ikapurwanti.foodappbinarchallenge.model.Cart
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceed
import com.ikapurwanti.foodappbinarchallenge.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CartRepository {
    fun getCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>>
    suspend fun createCart(menu: Menu, totalQty: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAllCart()
}

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource
) : CartRepository {
    override fun getCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>> {
        return cartDataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf {
                        val qty = it.itemQuantity
                        val pricePerItem = it.menuPrice
                        qty * pricePerItem
                    }
                    Pair(result, totalPrice)
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
        return menu.id?.let{ menuId ->
            proceedFlow {
                val affectedRow = cartDataSource.insertCart(
                    CartEntity(
                        menuId = menuId,
                        itemQuantity = totalQty,
                        menuImgUrl = menu.imageUrl,
                        menuPrice =  menu.price,
                        menuName = menu.name
                    )
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

    override suspend fun deleteAllCart() {
        return cartDataSource.deleteAllCartItems()
    }

}