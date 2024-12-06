package com.example.desafio1_appvader.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tipo(
    @SerializedName("id")
    val id:Int,
    @SerializedName("tipo")
    val tipo:String
): Serializable{
    override fun toString(): String {
        return tipo
    }
}
