package com.ikapurwanti.foodappbinarchallenge.data.network.api.model.order


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderItemRequest(
    @SerializedName("catatan")
    val notes: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("qty")
    val qty: Int?
)