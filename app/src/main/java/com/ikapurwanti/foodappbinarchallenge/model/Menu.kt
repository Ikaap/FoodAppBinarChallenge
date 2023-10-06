package com.ikapurwanti.foodappbinarchallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var id: Int? = null,
    val menuImg: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val desc: String = ""
) : Parcelable
