package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Estadisticas(val idUsuario:Int, var nivel:String, val misionesPendientes:Int, val misionesCompletadas:Int, val misionesFallidas:Int)
