package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioPerfil(
    @SerializedName("id")
    val id:Int,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("foto")
    val foto:String?
): Serializable
