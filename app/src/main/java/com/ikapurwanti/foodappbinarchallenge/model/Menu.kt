package com.ikapurwanti.foodappbinarchallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var image : Int = 0,
    var name : String = "",
    var price : Double = 0.0,
    var rating : Double = 0.0,
    var desc : String =""
) : Parcelable
