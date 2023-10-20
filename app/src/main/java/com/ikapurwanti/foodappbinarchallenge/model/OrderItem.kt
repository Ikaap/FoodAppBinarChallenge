package com.ikapurwanti.foodappbinarchallenge.model

import com.google.gson.annotations.SerializedName
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.order.OrderItemRequest

data class OrderItem(
    val notes: String,
    val price: Int,
    val name: String,
    val qty: Int
)

fun OrderItem.toOrderItemRequest() = OrderItemRequest(
    notes, price, name, qty
)