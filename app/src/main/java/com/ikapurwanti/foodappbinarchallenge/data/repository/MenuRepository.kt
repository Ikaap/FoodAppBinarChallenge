package com.ikapurwanti.foodappbinarchallenge.data.repository

import com.ikapurwanti.foodappbinarchallenge.data.network.api.datasource.RestaurantDataSource
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.category.toCategoryList
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu.toMenuList
import com.ikapurwanti.foodappbinarchallenge.model.Category
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getMenu(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val apiDataSource: RestaurantDataSource
) : MenuRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getMenu(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenu(category).data?.toMenuList() ?: emptyList()
        }
    }

}