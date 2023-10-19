package com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource

import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.category.CategoriesResponse
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu.MenuResponse
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.order.OrderRequest
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.order.OrderResponse
import com.ikapurwanti.foodappbinarchallenge.data.network.api.service.RestaurantService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RestaurantDataSource {
    suspend fun getMenu(category: String? = null): MenuResponse

    suspend fun getCategories(): CategoriesResponse

    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse

}

class  RestaurantApiDataSource(private val service: RestaurantService): RestaurantDataSource {
    override suspend fun getMenu(category: String?): MenuResponse {
        return service.getMenu(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }
}
