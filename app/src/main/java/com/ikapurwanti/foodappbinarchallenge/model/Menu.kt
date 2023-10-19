package com.ikapurwanti.foodappbinarchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val imageUrl: String,
    val name: String,
    val formattedPrice: String,
    val price: Int,
    val rating: Double,
    val desc: String,
    val address: String,
) : Parcelable
