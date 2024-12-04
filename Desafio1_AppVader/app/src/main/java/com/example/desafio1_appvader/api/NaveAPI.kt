package com.example.desafio1_appvader.api

import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.nave.Nave
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NaveAPI {
    //NAVE
    @POST("registrarNave")
    suspend fun registrarNave(@Body nave: Nave): Response<Boolean>

    @DELETE("eliminarNave/{matricula}")
    suspend fun eliminarNave(@Path("matricula") matricula:String): Response<Boolean>

    @GET("naves")
    suspend fun obtenerNaves(): Response<MutableList<Nave>>

    @GET("naves/{matricula}")
    suspend fun obtenerNavePorMatricula(@Path("matricula") matricula:String): Response<Nave?>

    @GET("navesPorTipo/{tipo}")
    suspend fun obtenerNavesPorTipo(@Path("tipo") tipo:String): Response<MutableList<Nave>>

    @GET("tiposNaves")
    suspend fun obtenerTiposNaves(): Response<MutableList<Tipo>>




}