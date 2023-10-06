package com.ikapurwanti.foodappbinarchallenge.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "menu")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "menu_img")
    val menuImg: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "desc")
    val desc: String
)