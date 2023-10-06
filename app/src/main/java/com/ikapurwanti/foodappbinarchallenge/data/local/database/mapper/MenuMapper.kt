package com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper

import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.MenuEntity
import com.ikapurwanti.foodappbinarchallenge.model.Menu

fun MenuEntity?.toMenu() = Menu(
    id = this?.id ?: 0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    rating = this?.rating ?: 0.0,
    desc = this?.desc.orEmpty(),
    menuImg = this?.menuImg ?: 0
)

fun Menu?.toMenuEntity() = MenuEntity(
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    rating = this?.rating ?: 0.0,
    desc = this?.desc.orEmpty(),
    menuImg = this?.menuImg ?: 0
).apply {
    this@toMenuEntity?.id?.let {
        this.id = this@toMenuEntity.id
    }
}

fun List<MenuEntity?>.toMenuList() = this.map { it.toMenu() }

fun List<Menu?>.toMenuEntity()= this.map { it.toMenuEntity() }