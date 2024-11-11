package com.example.desafio1_appvader.api

import com.example.desafio1_appvader.modelo.Asignacion
import com.example.desafio1_appvader.modelo.Mision
import com.example.desafio1_appvader.modelo.MisionBombardeo
import com.example.desafio1_appvader.modelo.MisionCaza
import com.example.desafio1_appvader.modelo.MisionVuelo
import com.example.desafio1_appvader.modelo.Nave
import com.example.desafio1_appvader.modelo.Nivel
import com.example.desafio1_appvader.modelo.Rol
import com.example.desafio1_appvader.modelo.Usuario
import com.example.desafio1_appvader.modelo.UsuarioLogIn
import com.example.desafio1_appvader.modelo.UsuarioPerfil
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MainAPI {
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
    suspend fun obtenerRolPorId(@Path("id") idUsuario:Int): Response<Rol?>

    @GET("nivel/{id}")
    suspend fun obtenerNivelPorId(@Path("id") idUsuario:Int): Response<Nivel?>



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



    //MISION
    @POST("registrarMision")
    suspend fun registrarMision(@Body mision: Mision): Response<Boolean>

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

    @GET("asignacion/{id}")
    suspend fun obtenerAsignacionPorId(@Path("id") id:Int): Response<Asignacion?>

    @PUT("actualizarEstado/{id}")
    suspend fun actualizarEstado(@Path("id") id:Int, @Body estado: Int): Response<Boolean>
}