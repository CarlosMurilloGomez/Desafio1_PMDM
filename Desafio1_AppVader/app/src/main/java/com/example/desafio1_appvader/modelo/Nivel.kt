package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Nivel(
    @SerializedName("nivel")
    val nivel:String
): Serializable