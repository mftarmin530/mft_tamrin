package com.example.mft_public

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ahad on 11/29/2024.
 */
object RetrofitProvider {

    private const val BASE_URL = "http://65.109.201.143:8080"
    fun getRetrofit() = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()


}