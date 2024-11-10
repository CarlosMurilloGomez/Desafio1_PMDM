package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MisionCaza(
    @SerializedName("idMision")
    val idMision:Int,
    @SerializedName("objetivos")
    val objetivos:Int
): Serializable
