package com.ikapurwanti.foodappbinarchallenge.data

import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.model.Categories


interface CategoriesDataSource {
    fun getCategories(): List<Categories>
}

class CategoriesDataSourceImpl() : CategoriesDataSource {
    override fun getCategories(): List<Categories> {
        return listOf(
            Categories(
                R.drawable.img_ctg_makanan,
                "Chicken"
            ),
            Categories(
                R.drawable.img_ctg_burger,
                "Burger"
            ),
            Categories(
                R.drawable.img_ctg_coffee,
                "Coffee"
            ),
            Categories(
                R.drawable.img_ctg_mie,
                "Noodle"
            ),
            Categories(
                R.drawable.img_ctg_snack,
                "Snack"
            ),
            Categories(
                R.drawable.img_ctg_minuman,
                "Drink"
            ),
        )
    }


}
