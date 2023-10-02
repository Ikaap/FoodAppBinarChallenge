package com.ikapurwanti.foodappbinarchallenge.data

import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.model.Menu


interface MenuDataSource {
    fun getMenu(): List<Menu>
}

class MenuDataSourceImpl() : MenuDataSource {
    override fun getMenu(): List<Menu> {
        return listOf(
            Menu(
                R.drawable.img_menu_bakpao,
                "Bakpao",
                30000.0,
                4.0,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_ayam_bakar,
                "Grilled Chicken",
                40000.0,
                4.2,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_coffee,
                "Coffee Milk",
                25000.0,
                4.8,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_dimsum,
                "Dim sum",
                37000.0,
                4.5,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_ayam_goreng,
                "Fried Chicken",
                20000.0,
                4.2,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_ayam_panggang,
                "Honey Roast Chicken",
                50000.0,
                5.0,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_pizza,
                "Pizza",
                70000.0,
                4.4,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_sate_taichan,
                "Taichan Satay",
                40000.0,
                3.0,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_spaghetti,
                "Spaghetti",
                73000.0,
                4.7,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_sate_ayam,
                "Chicken Satay",
                25000.0,
                3.9,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_sushi,
                "Sushi",
                80000.0,
                4.6,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            ),
            Menu(
                R.drawable.img_menu_coffee_latte,
                "Coffee Latte",
                30000.0,
                5.0,
                "lorem ipsum dolor sit amet lorem port lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor lorem ipsum dolor"
            )
        )
    }


}
