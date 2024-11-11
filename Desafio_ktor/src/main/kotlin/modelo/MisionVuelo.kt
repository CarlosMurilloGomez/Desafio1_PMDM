package modelo

import kotlinx.serialization.Serializable

@Serializable
data class MisionVuelo(val idMision:Int, val duracion:Int, val carga:Int, val pasajeros:Int)
