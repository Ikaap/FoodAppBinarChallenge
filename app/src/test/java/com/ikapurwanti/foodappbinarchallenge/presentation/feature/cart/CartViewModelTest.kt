package com.ikapurwanti.foodappbinarchallenge.presentation.feature.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ikapurwanti.foodappbinarchallenge.data.repository.CartRepository
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import com.tools.MainCoroutineRule
import com.tools.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {

    @MockK
    private lateinit var cartRepo: CartRepository

    private lateinit var viewModel: CartViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { cartRepo.getCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                            mockk(relaxed = true)
                        ),
                        150000
                    )
                )
            )
        }
        viewModel = spyk(CartViewModel(cartRepo))

        val resultMock = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { cartRepo.decreaseCart(any()) } returns resultMock
        coEvery { cartRepo.increaseCart(any()) } returns resultMock
        coEvery { cartRepo.deleteCart(any()) } returns resultMock
        coEvery { cartRepo.setCartNotes(any()) } returns resultMock
    }

    @Test
    fun `test cart list`() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 3)
        assertEquals(result.payload?.second, 150000)
    }

    @Test
    fun `test decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { cartRepo.decreaseCart(any()) }
    }

    @Test
    fun `test increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { cartRepo.increaseCart(any()) }
    }

    @Test
    fun `test delete cart`() {
        viewModel.deleteCart(mockk())
        coVerify { cartRepo.deleteCart(any()) }
    }

    @Test
    fun `test set cart notes`() {
        viewModel.setCartNotes(mockk())
        coVerify { cartRepo.setCartNotes(any()) }
    }
}
