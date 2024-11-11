package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioLogIn(
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("password")
    val password:String
): Serializable