package com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.ikapurwanti.foodappbinarchallenge.model.Menu

@Keep
data class MenuItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("alamat_resto")
    val address: String?,
    @SerializedName("detail")
    val desc: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("rating")
    val rating: Double?
)

fun MenuItemResponse.toMenu() = Menu(
    address = this.address.orEmpty(),
    desc = this.desc.orEmpty(),
    price = this.price ?: 0,
    formattedPrice = this.formattedPrice.orEmpty(),
    imageUrl = this.imageUrl.orEmpty(),
    name = this.name.orEmpty(),
    rating = this.rating ?: 0.0
)

fun Collection<MenuItemResponse>.toMenuList() = this.map { it.toMenu() }