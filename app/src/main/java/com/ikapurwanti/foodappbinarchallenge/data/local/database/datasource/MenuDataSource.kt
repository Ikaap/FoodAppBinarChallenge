package com.ikapurwanti.foodappbinarchallenge.data.local.database.datasource

import com.ikapurwanti.foodappbinarchallenge.data.local.database.dao.MenuDao
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

interface MenuDataSource {
    fun getAllMenu(): Flow<List<MenuEntity>>
    fun getMenuById(id: Int): Flow<MenuEntity>
    suspend fun insertMenu(menu: List<MenuEntity>)
    suspend fun deleteMenu(menu: MenuEntity): Int
    suspend fun updateMenu(menu: MenuEntity): Int
}

class MenuDatabaseDataSource(
    private val menuDao : MenuDao
): MenuDataSource{
    override fun getAllMenu(): Flow<List<MenuEntity>> {
        return menuDao.getAllMenu()
    }

    override fun getMenuById(id: Int): Flow<MenuEntity> {
        return menuDao.getMenuById(id)
    }

    override suspend fun insertMenu(menu: List<MenuEntity>) {
        return menuDao.insertMenu(menu)
    }

    override suspend fun deleteMenu(menu: MenuEntity): Int {
        return menuDao.deleteMenu(menu)
    }

    override suspend fun updateMenu(menu: MenuEntity): Int {
        return menuDao.updateMenu(menu)
    }

}