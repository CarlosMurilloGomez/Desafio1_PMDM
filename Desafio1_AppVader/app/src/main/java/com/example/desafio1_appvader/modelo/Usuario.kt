package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id")
    val id:Int,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("activo")
    val activo:Int,
    @SerializedName("foto")
    val foto:String?,
    @SerializedName("edad")
    val edad:Int,
    @SerializedName("experiencia")
    val experiencia:Int,
    @SerializedName("rol")
    val rol:Int
): Serializable
