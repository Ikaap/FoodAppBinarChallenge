package com.ikapurwanti.foodappbinarchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val address: String,
    val desc: String,
    val price: Int,
    val formattedPrice: String,
    val imageUrl: String,
    val name: String,
    val rating: Double
) : Parcelable
