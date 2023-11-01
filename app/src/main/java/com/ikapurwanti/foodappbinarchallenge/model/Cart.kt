package com.ikapurwanti.foodappbinarchallenge.model

data class Cart(
    var id: Int? = null,
    var menuId: Int? = null,
    var menuName: String,
    var menuPrice: Int,
    var menuImgUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null
)
