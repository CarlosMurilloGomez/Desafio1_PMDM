package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Mision(val id:Int, val nombre:String, val exp:Int, val naveAsig:Int, val tipo:Int)
