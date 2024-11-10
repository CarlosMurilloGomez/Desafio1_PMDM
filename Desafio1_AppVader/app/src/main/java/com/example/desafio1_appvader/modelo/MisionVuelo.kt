package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MisionVuelo(
    @SerializedName("idMision")
    val idMision:Int,
    @SerializedName("duracion")
    val duracion:Int,
    @SerializedName("carga")
    val carga:Int,
    @SerializedName("pasajeros")
    val pasajeros:Int
): Serializable
