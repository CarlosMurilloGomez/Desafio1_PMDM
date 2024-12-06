package com.example.desafio1_appvader.api

import com.example.desafio1_appvader.parametros.Parametros
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MisionNetwork {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+ Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MisionAPI::class.java)

    }
}