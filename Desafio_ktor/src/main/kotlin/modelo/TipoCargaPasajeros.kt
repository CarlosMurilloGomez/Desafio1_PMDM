package modelo

import kotlinx.serialization.Serializable

@Serializable
data class TipoCargaPasajeros(
    val tipo: Int,
    val carga: Int,
    val pasajeros: Int
)