package com.ikapurwanti.foodappbinarchallenge.data.network.api.service

import com.ikapurwanti.foodappbinarchallenge.BuildConfig
import com.ikapurwanti.foodappbinarchallenge.data.network.api.model.menu.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantService {

    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String): MenuResponse



    companion object {
        @JvmStatic
        operator fun invoke(): RestaurantService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RestaurantService::class.java)
        }
    }
}