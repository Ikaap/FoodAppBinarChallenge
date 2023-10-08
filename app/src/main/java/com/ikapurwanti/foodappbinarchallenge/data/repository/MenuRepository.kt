package com.ikapurwanti.foodappbinarchallenge.data.repository

import com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource.MenuDataSource
import com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper.toMenuList
import com.ikapurwanti.foodappbinarchallenge.model.Menu
import com.ikapurwanti.foodappbinarchallenge.utils.ResultWrapper
import com.ikapurwanti.foodappbinarchallenge.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface MenuRepository {
    fun getMenu(): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val menuDataSource: MenuDataSource,
) : MenuRepository {
    override fun getMenu(): Flow<ResultWrapper<List<Menu>>> {
        return menuDataSource.getAllMenu().map {
            proceed { it.toMenuList() }
        }.map {
            if (it.payload?.isEmpty() == true)
                ResultWrapper.Empty(it.payload)
            else
                it
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(1500)
        }
    }

}