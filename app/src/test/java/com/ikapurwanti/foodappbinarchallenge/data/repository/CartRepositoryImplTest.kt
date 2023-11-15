package com.ikapurwanti.foodappbinarchallenge.data.repository

import app.cash.turbine.test
import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.CartDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.CartEntity
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.order.OrderResponse
import com.ikapurwanti.foodappbinarchallenge.model.Cart
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {

    @MockK
    lateinit var localDataSource: CartDataSource

    @MockK
    lateinit var networkDataSource: RestaurantDataSource

    private lateinit var cartRepo: CartRepository

    private val fakeCartList = listOf(
        CartEntity(
            id = 1,
            menuId = 1,
            menuName = "name menu",
            menuPrice = 18000,
            menuImgUrl = "menu url",
            itemQuantity = 2,
            itemNotes = "notes"
        ),
        CartEntity(
            id = 2,
            menuId = 2,
            menuName = "name menu",
            menuPrice = 12000,
            menuImgUrl = "menu url",
            itemQuantity = 2,
            itemNotes = "notes"
        )
    )

    val mockMenu = mockk<Menu>(relaxed = true)

    val mockCart = Cart(
        id = 1,
        menuId = 1,
        menuName = "name menu",
        menuPrice = 18000,
        menuImgUrl = "menu url",
        itemQuantity = 2,
        itemNotes = "notes"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartRepo = CartRepositoryImpl(localDataSource, networkDataSource)
    }

    @Test
    fun `get user card data, result success`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(fakeCartList)
        }
        runTest {
            cartRepo.getCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.first?.size, 2)
                assertEquals(data.payload?.second, 60000)
                verify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user card data, result loading`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(fakeCartList)
        }
        runTest {
            cartRepo.getCartData().map {
                delay(100)
                it
            }.test {
                delay(2110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                verify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user card data, result error`() {
        every { localDataSource.getAllCarts() } returns flow {
            throw IllegalStateException("Error")
        }
        runTest {
            cartRepo.getCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                verify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user card data, result empty`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(listOf())
        }
        runTest {
            cartRepo.getCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                verify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `create cart loading, menu id not null`() {
        runTest {
            coEvery { localDataSource.insertCart(any()) } returns 1
            cartRepo.createCart(mockMenu, 1).map {
                delay(100)
                it
            }.test {
                delay(110)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart success, menu id not null`() {
        runTest {
            coEvery { localDataSource.insertCart(any()) } returns 1
            cartRepo.createCart(mockMenu, 1).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                coVerify { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart error, menu id not null`() {
        runTest {
            coEvery { localDataSource.insertCart(any()) } throws IllegalStateException("Error")
            cartRepo.createCart(mockMenu, 1).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart error, menu id null`() {
        runTest {
            val mockMenu = mockk<Menu>(relaxed = true) {
                every { id } returns null
            }
            coEvery { localDataSource.insertCart(any()) } returns 1
            cartRepo.createCart(mockMenu, 1).map {
                delay(100)
                it
            }.test {
                delay(210)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                assertEquals(result.exception?.message, "Menu ID not found")
                coVerify(atLeast = 0) { localDataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart when quantity less than or equals 0`() {
        val mockCart = Cart(
            id = 1,
            menuId = 1,
            menuName = "name menu",
            menuPrice = 18000,
            menuImgUrl = "menu url",
            itemQuantity = 0,
            itemNotes = "notes"
        )
        coEvery { localDataSource.deleteCart(any()) } returns 1
        coEvery { localDataSource.updateCart(any()) } returns 1
        runTest {
            cartRepo.decreaseCart(mockCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 1) { localDataSource.deleteCart(any()) }
                    coVerify(atLeast = 0) { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `decrease cart when quantity more than 0`() {
        val mockCart = Cart(
            id = 1,
            menuId = 1,
            menuName = "name menu",
            menuPrice = 18000,
            menuImgUrl = "menu url",
            itemQuantity = 2,
            itemNotes = "notes"
        )
        coEvery { localDataSource.deleteCart(any()) } returns 1
        coEvery { localDataSource.updateCart(any()) } returns 1
        runTest {
            cartRepo.decreaseCart(mockCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 0) { localDataSource.deleteCart(any()) }
                    coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `increase cart`() {
        coEvery { localDataSource.updateCart(any()) } returns 1
        runTest {
            cartRepo.increaseCart(mockCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `set cart notes`() {
        coEvery { localDataSource.updateCart(any()) } returns 1
        runTest {
            cartRepo.setCartNotes(mockCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `delete cart`() {
        coEvery { localDataSource.deleteCart(any()) } returns 1
        runTest {
            cartRepo.deleteCart(mockCart)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 1) { localDataSource.deleteCart(any()) }
                }
        }
    }

    @Test
    fun deleteAll() {
        coEvery { localDataSource.deleteAllCartItems() } returns Unit
        runTest {
            val result = cartRepo.deleteAllCart()
            coVerify { localDataSource.deleteAllCartItems() }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun `test order`() {
        runTest {
            val mockCarts = listOf(
                Cart(
                    id = 1,
                    menuId = 1,
                    menuName = "name menu",
                    menuPrice = 18000,
                    menuImgUrl = "menu url",
                    itemQuantity = 2,
                    itemNotes = "notes"
                ),
                Cart(
                    id = 2,
                    menuId = 2,
                    menuName = "name menu dua",
                    menuPrice = 12000,
                    menuImgUrl = "menu url",
                    itemQuantity = 2,
                    itemNotes = "notes"
                )
            )
            coEvery { networkDataSource.createOrder(any()) } returns OrderResponse(
                code = 200,
                message = "success",
                status = true
            )
            cartRepo.order(mockCarts).map {
                delay(100)
                it
            }.test {
                delay(220)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
            }
        }
    }
}
