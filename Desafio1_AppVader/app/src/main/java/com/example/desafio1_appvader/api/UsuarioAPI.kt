package com.example.desafio1_appvader.api

import com.example.desafio1_appvader.modelo.Cadena
import com.example.desafio1_appvader.modelo.mision.Asignacion
import com.example.desafio1_appvader.modelo.mision.Mision
import com.example.desafio1_appvader.modelo.mision.MisionBombardeo
import com.example.desafio1_appvader.modelo.mision.MisionCaza
import com.example.desafio1_appvader.modelo.mision.MisionVuelo
import com.example.desafio1_appvader.modelo.nave.Nave
import com.example.desafio1_appvader.modelo.usuario.Usuario
import com.example.desafio1_appvader.modelo.usuario.UsuarioLogIn
import com.example.desafio1_appvader.modelo.usuario.UsuarioPerfil
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioAPI {
    //USUARIO
    @POST("registrarUsuario")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<Boolean>

    @PUT("modificarPerfilUsuario")
    suspend fun modificarPerfilUsuario(@Body userData: UsuarioPerfil): Response<Boolean>

    @PUT("activarCuenta/{id}")
    suspend fun activarCuenta(@Path("id") id:Int): Response<Boolean>

    @PUT("modificarExperienciaUsuario/{id}")
    suspend fun modificarExperienciaUsuario(@Path("id") id:Int, @Body experiencia: Int): Response<Boolean>

    @DELETE("eliminarUsuario/{id}")
    suspend fun eliminarUsuario(@Path("id") id:Int): Response<Boolean>

    @POST("login")
    suspend fun login(@Body datosLogIn: UsuarioLogIn): Response<Usuario?>

    @GET("usuarios")
    suspend fun obtenerUsuarios(): Response<MutableList<Usuario>>

    @GET("usuarios/{id}")
    suspend fun obtenerUsuarioPorId(@Path("id") id:Int): Response<Usuario?>

    @GET("pilotos")
    suspend fun obtenerPilotos(): Response<MutableList<Usuario>>

    @GET("ranking")
    suspend fun obtenerRanking(): Response<MutableList<Usuario>>

    @GET("rol/{id}")
    suspend fun obtenerRolPorId(@Path("id") idUsuario:Int): Response<Cadena?>

    @GET("nivel/{id}")
    suspend fun obtenerNivelPorId(@Path("id") idUsuario:Int): Response<Cadena?>





}