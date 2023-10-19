package com.ikapurwanti.foodappbinarchallenge.data.dummy

import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.model.Menu


interface DummyMenuDataSource {
    fun getMenu(): List<Menu>
}

class DummyMenuDataSourceImpl() : DummyMenuDataSource {
    override fun getMenu(): List<Menu> {
        return listOf(
            Menu(
                1,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                25000,
                "Rp. 25.000",
                "https://images.tokopedia.net/img/cache/250-square/hDjmkQ/2022/6/3/96472a39-7a63-4179-9f7d-44cf0959982a.png",
                "French Fries",
                4.5
            ),
            Menu(
                2,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                5000,
                "Rp. 5.000",
                "https://assets.klikindomaret.com/share/20116110_1.jpg",
                "Maxicorn Roasted",
                4.0
            ),
            Menu(
                3,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                3000,
                "Rp. 3.000",
                "https://cdn.shopify.com/s/files/1/0566/6540/7545/files/beng-beng.png?v=1671622247",
                "beng-beng",
                4.9
            ),
            Menu(
                4,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                1000,
                "Rp. 1.000",
                "https://pbs.twimg.com/media/Eg4ciSJVgAYSd3U.jpg",
                "Choki-Choki",
                4.5
            ),
            Menu(
                5,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                1500,
                "Rp. 1.500",
                "https://s0.bukalapak.com/img/5501585483/large/2_ciki_zeky.jpg",
                "Zeky",
                4.3
            ),
            Menu(
                6,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                25000000,
                "Rp. 25.000.000",
                "https://www.allrecipes.com/thmb/5JVfA7MxfTUPfRerQMdF-nGKsLY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/25473-the-perfect-basic-burger-DDMFS-4x3-56eaba3833fd4a26a82755bcd0be0c54.jpg",
                "Basic Burger",
                5.0
            ),
            Menu(
                7,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                40000000,
                "Rp. 40.000.000",
                "https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/IDITE20230915062857025767/detail/45a3dc24_wz9_size_800.webp",
                "Burger Kings",
                4.7
            ),
            Menu(
                8,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                10000000,
                "Rp. 10.000.000",
                "https://media-cdn.tripadvisor.com/media/photo-s/17/79/05/28/coi-cheese-burger.jpg",
                "Burger Coi",
                4.3
            ),
            Menu(
                9,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "Olahan mi instan emang paling bisa menggugah selera. Salah satu olahan mi instan yang banyak diminati adalah mi dog-dog.",
                5000,
                "Rp. 5.000",
                "https://cdn-image.hipwee.com/wp-content/uploads/2019/06/hipwee-Depositphotos_295327104_xl-2015.jpg",
                "Mie Dogdog",
                4.8
            ),
            Menu(
                10,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                10000,
                "Rp. 10.000",
                "https://i.ytimg.com/vi/99vJxxF_Ypw/maxresdefault.jpg",
                "Mie Burungdara",
                4.9
            ),
            Menu(
                11,
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                10000,
                "Rp. 10.000",
                "https://i0.wp.com/resepkoki.id/wp-content/uploads/2021/03/Resep-Mie-Aceh.jpg?fit=996%2C1328&ssl=1",
                "Mie Aceh",
                5.0
            ),
        )
    }


}
