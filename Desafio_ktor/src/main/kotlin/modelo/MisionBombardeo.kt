package modelo

import kotlinx.serialization.Serializable

@Serializable
data class MisionBombardeo (val idMision:Int, val objetivos:Int, val carga:Int, val pasajeros:Int)