package com.ikapurwanti.foodappbinarchallenge.model

data class Cart(
    var id: Int? = null,
    var menuId: Int = 0,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null
)
