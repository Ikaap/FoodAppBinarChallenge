package com.ikapurwanti.foodappbinarchallenge.data.repository

import app.cash.turbine.test
import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.category.CategoriesResponse
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.category.CategoryResponse
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu.MenuItemResponse
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu.MenuResponse
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {

    @MockK
    lateinit var networkDataSource: RestaurantDataSource

    private lateinit var menuRepository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuRepository = MenuRepositoryImpl(networkDataSource)
    }

    @Test
    fun `get categories, with result loading`() {
        val mockCategoryResponse = mockk<CategoriesResponse>()
        runTest {
            coEvery { networkDataSource.getCategories() } returns mockCategoryResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { networkDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success`() {
        val fakeCategoryResponse = CategoryResponse(
            imageUrl = "url",
            name = "name1"
        )
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "success",
            data = listOf(fakeCategoryResponse)
        )
        runTest {
            coEvery { networkDataSource.getCategories() } returns fakeCategoriesResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "name1")
                coVerify { networkDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result empty`() {
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "success",
            data = emptyList()
        )
        runTest {
            coEvery { networkDataSource.getCategories() } returns fakeCategoriesResponse
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { networkDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result error`() {
        runTest {
            coEvery { networkDataSource.getCategories() } throws IllegalStateException("Error")
            menuRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { networkDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get menu, with result loading`() {
        val mockMenuResponse = mockk<MenuResponse>()
        runTest {
            coEvery { networkDataSource.getMenu(any()) } returns mockMenuResponse
            menuRepository.getMenu("snack").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { networkDataSource.getMenu(any()) }
            }
        }
    }

    @Test
    fun `get menu, with result success`() {
        val fakeMenuItemResponse = MenuItemResponse(
            id = 1,
            imageUrl = "url",
            name = "name",
            formattedPrice = "price",
            price = 120000,
            rating = 3.0,
            desc = "desc",
            address = "address"
        )
        val fakeMenuResponse = MenuResponse(
            code = 200,
            data = listOf(fakeMenuItemResponse),
            message = "success",
            status = true
        )
        runTest {
            coEvery { networkDataSource.getMenu(any()) } returns fakeMenuResponse
            menuRepository.getMenu("snack").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, 1)
                coVerify { networkDataSource.getMenu(any()) }
            }
        }
    }

    @Test
    fun `get menu, with result empty`() {
        val fakeMenuResponse = MenuResponse(
            code = 200,
            data = emptyList(),
            message = "success",
            status = true
        )
        runTest {
            coEvery { networkDataSource.getMenu(any()) } returns fakeMenuResponse
            menuRepository.getMenu("snack").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { networkDataSource.getMenu(any()) }
            }
        }
    }

    @Test
    fun `get menu, with result error`() {
        runTest {
            coEvery { networkDataSource.getMenu(any()) } throws IllegalStateException("Error")
            menuRepository.getMenu("snack").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { networkDataSource.getMenu(any()) }
            }
        }
    }
}
