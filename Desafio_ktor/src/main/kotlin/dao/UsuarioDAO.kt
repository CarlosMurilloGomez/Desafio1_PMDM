package dao

import modelo.*

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun actualizarPerfil(perfil: UsuarioPerfil): Boolean
    fun activarCuenta(id: Int): Boolean
    fun actualizarExperiencia(expGanada: Int, id: Int): Boolean
    fun eliminar(id: Int): Boolean

    fun obtenerPilotos(): List<Usuario>
    fun obtenerRankingPilotos(): List<Usuario>
    fun obtenerUsuarioPorId(id: Int): Usuario?
    fun obtenerUsuarioPorNombre(nombre: String): Usuario?
    fun obtenerRolPorId(id: Int): Cadena?
    fun obtenerNivelPorId(id: Int): Cadena?
    fun obtenerEstadisticasPorId(id: Int): Estadisticas?
}