package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mision(
    @SerializedName("id")
    val id:Int,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("exp")
    val exp:Int,
    @SerializedName("naveAsig")
    val naveAsig:Int,
    @SerializedName("tipo")
    val tipo:Int
): Serializable
