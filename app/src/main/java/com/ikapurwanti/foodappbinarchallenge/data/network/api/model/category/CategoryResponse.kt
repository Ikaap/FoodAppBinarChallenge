package com.ikapurwanti.foodappbinarchallenge.data.network.api.model.category


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.ikapurwanti.foodappbinarchallenge.model.Category

@Keep
data class CategoryResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryResponse.toCategory() = Category(
    imageUrl = this.imageUrl.orEmpty(),
    name = this.name.orEmpty()
)

fun Collection<CategoryResponse>.toCategoryList() = this.map {
    it.toCategory()
}