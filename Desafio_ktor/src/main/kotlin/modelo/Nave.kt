package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Nave(val matricula: String, val foto: String, val tipo:Int, val carga:Int, val pasajeros:Int)
