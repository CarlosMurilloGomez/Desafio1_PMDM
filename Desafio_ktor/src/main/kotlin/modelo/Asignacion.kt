package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Asignacion(val id:Int, val idMision:Int, val idUsuario:Int, val estado:Int?)
