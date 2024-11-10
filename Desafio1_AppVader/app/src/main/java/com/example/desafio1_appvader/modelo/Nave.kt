package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Nave(
    @SerializedName("matricula")
    val matricula: String,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("tipo")
    val tipo:Int,
    @SerializedName("carga")
    val carga:Int,
    @SerializedName("pasajeros")
    val pasajeros:Int
): Serializable
