package com.ikapurwanti.foodappbinarchallenge.data.local.database.mapper

import com.ikapurwanti.foodappbinarchallenge.data.local.database.entity.CartEntity
import com.ikapurwanti.foodappbinarchallenge.model.Cart

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0,
    menuImgUrl = this?.menuImgUrl.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0,
    menuImgUrl = this?.menuImgUrl.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }
//
//fun CartMenuRelation.toCartMenu() = CartMenu(
//    cart = this.cart.toCart(),
//    menu = this.menu.toMenu()
//)
//
//fun List<CartMenuRelation>.toCartMenuList() = this.map { it.toCartMenu() }