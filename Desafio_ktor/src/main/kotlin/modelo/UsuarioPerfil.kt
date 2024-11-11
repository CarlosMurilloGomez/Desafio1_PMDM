package modelo

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioPerfil(val id:Int, val nombre:String, val password:String, val foto:String?)
