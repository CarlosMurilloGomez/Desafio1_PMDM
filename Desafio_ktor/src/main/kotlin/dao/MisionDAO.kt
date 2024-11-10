package dao

import modelo.*


interface MisionDAO {
    fun insertarMision(mision: Mision): Boolean
    fun insertarVuelo(vuelo: MisionVuelo): Boolean
    fun insertarBombardeo(bombardeo: MisionBombardeo): Boolean
    fun insertarCaza(caza: MisionCaza): Boolean
    fun eliminar(idMision: Int): Boolean

    fun obtenerMisiones(): List<Mision>
    fun obtenerMisionPorId(idMision:Int): Mision?
    fun obtenerVueloPorId(idMision:Int): MisionVuelo?
    fun obtenerBombardeoPorId(idMision:Int): MisionBombardeo?
    fun obtenerCazaPorId(idMision:Int): MisionCaza?

    fun asignarMision(asignacion: Asignacion): Boolean
    fun obtenerAsignacionesPorIdUsuario(idUsuario:Int): List<Asignacion>
    fun obtenerAsignacionPorId(idAsig:Int): Asignacion?
    fun actualizarEstado(estado:Int, idAsignacion:Int): Boolean
}