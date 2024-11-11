package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MisionBombardeo (
    @SerializedName("idMision")
    val idMision:Int,
    @SerializedName("objetivos")
    val objetivos:Int,
    @SerializedName("carga")
    val carga:Int,
    @SerializedName("pasajeros")
    val pasajeros:Int
): Serializable