package com.example.desafio1_appvader.modelo.usuario

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioPerfil(
    @SerializedName("id")
    val id:Int,
    @SerializedName("password")
    val password:String,
    @SerializedName("foto")
    val foto:String?
): Serializable
