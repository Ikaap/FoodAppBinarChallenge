package com.ikapurwanti.foodappbinarchallenge.data.dummy

import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.model.Category


interface DummyCategoriesDataSource {
    fun getCategories(): List<Category>
}

class DummyCategoriesDataSourceImpl() : DummyCategoriesDataSource {
    override fun getCategories(): List<Category> {
        return listOf(
            Category(
                "R.drawable.img_ctg_makanan",
                "Chicken"
            ),
            Category(
                "R.drawable.img_ctg_burger",
                "Burger"
            ),
            Category(
                "R.drawable.img_ctg_coffee",
                "Coffee"
            ),
            Category(
                "R.drawable.img_ctg_mie",
                "Noodle"
            ),
            Category(
                "R.drawable.img_ctg_snack",
                "Snack"
            ),
            Category(
                "R.drawable.img_ctg_minuman",
                "Drink"
            ),
        )
    }


}
