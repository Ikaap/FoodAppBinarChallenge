package com.ikapurwanti.foodappbinarchallenge.data.local.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.CartEntity
import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.MenuEntity

data class CartMenuRelation (
    @Embedded
    val cart: CartEntity,
    @Relation(parentColumn = "menu_id", entityColumn = "id")
    val menu: MenuEntity
)
