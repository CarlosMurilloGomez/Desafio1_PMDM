package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Tipo(
    val id: Int,
    val tipo: String
)
