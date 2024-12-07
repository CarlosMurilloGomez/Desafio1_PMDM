package com.example.desafio1_appvader.modelo.mision

import com.google.gson.annotations.SerializedName

data class MisionMostrar (
    @SerializedName("id")
    val id:Int,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("exp")
    val exp:Int,
    @SerializedName("naveAsig")
    val naveAsig:String,
    @SerializedName("tipo")
    var tipo:String,
    @SerializedName("idAsignacion")
    var idAsignacion:Int,
    @SerializedName("estado")
    var estado:Int

)