package com.ikapurwanti.foodappbinarchallenge.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM MENU")
    fun getAllMenu(): Flow<List<MenuEntity>>

    @Query("SELECT * FROM MENU WHERE id ==:id")
    fun getMenuById(id: Int): Flow<MenuEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: List<MenuEntity>)

    @Delete
    suspend fun deleteMenu(menu: MenuEntity): Int

    @Update
    suspend fun updateMenu(menu: MenuEntity): Int
}