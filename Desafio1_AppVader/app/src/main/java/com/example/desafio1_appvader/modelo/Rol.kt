package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rol(
    @SerializedName("nombreRol")
    val nombreRol:String
): Serializable
