package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(val id:Int, val nombre:String, val password:String, val activo:Int, val foto:String?, val edad:Int, val experiencia:Int, val rol:Int)
