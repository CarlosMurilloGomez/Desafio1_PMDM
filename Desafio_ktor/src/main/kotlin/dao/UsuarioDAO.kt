package dao

import modelo.Nivel
import modelo.Rol
import modelo.Usuario
import modelo.UsuarioPerfil

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun actualizarPerfil(perfil: UsuarioPerfil): Boolean
    fun activarCuenta(id: Int): Boolean
    fun actualizarExperiencia(expGanada: Int, id: Int): Boolean
    fun eliminar(id: Int): Boolean

    fun obtenerUsuarios(): List<Usuario>
    fun obtenerPilotos(): List<Usuario>
    fun obtenerRankingPilotos(): List<Usuario>
    fun obtenerUsuarioPorId(id: Int): Usuario?
    fun obtenerUsuarioPorNombre(nombre: String): Usuario?
    fun obtenerRolPorId(id: Int): Rol?
    fun obtenerNivelPorId(id: Int): Nivel?
}