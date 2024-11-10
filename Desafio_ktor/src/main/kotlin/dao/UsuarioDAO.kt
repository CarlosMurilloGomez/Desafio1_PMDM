package dao

import modelo.Usuario
import modelo.UsuarioPerfil

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun actualizarPerfil(perfil: UsuarioPerfil): Boolean
    fun actualizarExperiencia(expGanada: Int, id: Int): Boolean
    fun eliminar(id: Int): Boolean

    fun obtenerUsuarios(): List<Usuario>
    fun obtenerPilotos(): List<Usuario>
    fun obtenerRankingPilotos(): List<Usuario>
    fun obtenerUsuarioPorId(id: Int): Usuario?
    fun obtenerUsuarioPorNombre(nombre: String): Usuario?
    fun obtenerRolPorId(id: Int): String?
    fun obtenerNivelPorId(id: Int): String?
}