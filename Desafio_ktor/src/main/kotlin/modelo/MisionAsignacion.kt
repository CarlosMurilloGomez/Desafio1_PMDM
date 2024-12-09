package modelo

import kotlinx.serialization.Serializable

@Serializable
data class MisionAsignacion(val id:Int, val nombre:String, val exp:Int, val naveAsig:String, val tipo:String, val idAsignacion: Int, val estado:Int)
