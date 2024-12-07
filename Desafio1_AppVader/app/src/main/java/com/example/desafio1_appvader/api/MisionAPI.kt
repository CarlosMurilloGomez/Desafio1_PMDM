package com.example.desafio1_appvader.api

import com.example.desafio1_appvader.modelo.Tipo
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionMostrar
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MisionAPI {

    //MISION
    @POST("registrarMision")
    suspend fun registrarMision(@Body mision: Mision): Response<Int>

    @POST("registrarVuelo")
    suspend fun registrarVuelo(@Body mision: MisionVuelo): Response<Boolean>

    @POST("registrarBombardeo")
    suspend fun registrarBombardeo(@Body mision: MisionBombardeo): Response<Boolean>

    @POST("registrarCaza")
    suspend fun registrarCaza(@Body mision: MisionCaza): Response<Boolean>

    @DELETE("eliminarMision/{id}")
    suspend fun eliminarMision(@Path("id") id:Int): Response<Boolean>

    @GET("misiones")
    suspend fun obtenerMisiones(): Response<MutableList<Mision>>

    @GET("misiones/{id}")
    suspend fun obtenerMisionPorId(@Path("id") id:Int): Response<Mision?>

    @GET("tiposMision")
    suspend fun obtenerTiposMision(): Response<MutableList<Tipo>>

    @GET("vuelo/{id}")
    suspend fun obtenerVueloPorId(@Path("id") idMision:Int): Response<MisionVuelo?>

    @GET("bombardeo/{id}")
    suspend fun obtenerBombardeoPorId(@Path("id") idMision:Int): Response<MisionBombardeo?>

    @GET("caza/{id}")
    suspend fun obtenerCazaPorId(@Path("id") idMision:Int): Response<MisionCaza?>

    @POST("asignarMision")
    suspend fun asignarMision(@Body asignacion: Asignacion): Response<Boolean>

    @GET("asignacionesPorUsuario/{id}")
    suspend fun obtenerAsignacionesPorUsuario(@Path("id") idUsuario:Int): Response<MutableList<Asignacion>>

    @GET("misionAsignacionesPorUsuario/{id}")
    suspend fun obtenerMisionAsignacionesPorUsuario(@Path("id") idUsuario:Int): Response<MutableList<MisionMostrar>>

    @GET("misionAsignacionesRealizadasPorUsuario/{id}")
    suspend fun obtenerMisionAsignacionesRealizadasPorUsuario(@Path("id") idUsuario:Int): Response<MutableList<MisionMostrar>>

    @GET("misionAsignacion/{id}")
    suspend fun obtenerMisionAsignacionPorId(@Path("id") id:Int): Response<MisionMostrar?>

    @PUT("actualizarEstado/{id}")
    suspend fun actualizarEstado(@Path("id") id:Int, @Body estado: Int): Response<Boolean>

}