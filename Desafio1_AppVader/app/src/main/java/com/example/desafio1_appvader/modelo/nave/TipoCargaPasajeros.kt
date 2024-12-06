package com.example.desafio1_appvader.modelo.nave

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TipoCargaPasajeros(
    @SerializedName("tipo")
    val tipo:Int,
    @SerializedName("carga")
    val carga:Int,
    @SerializedName("pasajeros")
    val pasajeros:Int
): Serializable
