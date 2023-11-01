package com.ikapurwanti.foodappbinarchallenge.data.dummy

import com.ikapurwanti.foodappbinarchallenge.model.Menu

interface DummyMenuDataSource {
    fun getMenu(): List<Menu>
}

class DummyMenuDataSourceImpl() : DummyMenuDataSource {
    override fun getMenu(): List<Menu> {
        return listOf(
            Menu(
                1,
                "https://images.tokopedia.net/img/cache/250-square/hDjmkQ/2022/6/3/96472a39-7a63-4179-9f7d-44cf0959982a.png",
                "French Fries",
                "Rp. 25.000",
                25000,
                4.5,
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345"
            ),
            Menu(
                2,
                "https://images.tokopedia.net/img/cache/250-square/hDjmkQ/2022/6/3/96472a39-7a63-4179-9f7d-44cf0959982a.png",
                "French Fries",
                "Rp. 25.000",
                25000,
                4.5,
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345"
            )

        )
    }
}
