package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Asignacion(
    @SerializedName("id")
    val id:Int,
    @SerializedName("idMision")
    val idMision:Int,
    @SerializedName("idUsuario")
    val idUsuario:Int,
    @SerializedName("estado")
    val estado:Int?
): Serializable
