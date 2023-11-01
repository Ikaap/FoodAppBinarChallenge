package com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.ikapurwanti.foodappbinarchallenge.model.Menu

@Keep
data class MenuItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("detail")
    val desc: String?,
    @SerializedName("alamat_resto")
    val address: String?
)

fun MenuItemResponse.toMenu() = Menu(
    id = this.id,
    address = this.address.orEmpty(),
    desc = this.desc.orEmpty(),
    price = this.price ?: 0,
    formattedPrice = this.formattedPrice.orEmpty(),
    imageUrl = this.imageUrl.orEmpty(),
    name = this.name.orEmpty(),
    rating = this.rating ?: 0.0
)

fun Collection<MenuItemResponse>.toMenuList() = this.map { it.toMenu() }
