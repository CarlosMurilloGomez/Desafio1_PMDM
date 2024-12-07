package dao

import modelo.*


interface MisionDAO {
    fun insertarMision(mision: Mision): Int?
    fun insertarVuelo(vuelo: MisionVuelo): Boolean
    fun insertarBombardeo(bombardeo: MisionBombardeo): Boolean
    fun insertarCaza(caza: MisionCaza): Boolean
    fun eliminar(idMision: Int): Boolean

    fun obtenerMisiones(): List<Mision>
    fun obtenerMisionPorId(idMision:Int): Mision?
    fun obtenerTiposMision(): List<Tipo>
    fun obtenerVueloPorId(idMision:Int): MisionVuelo?
    fun obtenerBombardeoPorId(idMision:Int): MisionBombardeo?
    fun obtenerCazaPorId(idMision:Int): MisionCaza?

    fun asignarMision(asignacion: Asignacion): Boolean
    fun obtenerAsignacionesPorIdUsuario(idUsuario:Int): List<Asignacion>
    fun obtenerMisionAsignacionesPorIdUsuario(idUsuario:Int): List<MisionAsignacion>
    fun obtenerMisionAsignacionesRealizadasPorIdUsuario(idUsuario: Int): List<MisionAsignacion>
    fun obtenerMisionAsignacionPorId(idAsig:Int): MisionAsignacion?
    fun actualizarEstado(estado:Int, idAsignacion:Int): Boolean
}