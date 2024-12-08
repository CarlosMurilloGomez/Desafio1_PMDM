package com.example.desafio1_appvader.modelo.usuario

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Estadisticas(
    @SerializedName("idUsuario")
    val idUsuario:Int,
    @SerializedName("nivel")
    val nivel:String,
    @SerializedName("misionesPendientes")
    val misionesPendientes:Int,
    @SerializedName("misionesCompletadas")
    val misionesCompletadas:Int,
    @SerializedName("misionesFallidas")
    val misionesFallidas:Int,
): Serializable
