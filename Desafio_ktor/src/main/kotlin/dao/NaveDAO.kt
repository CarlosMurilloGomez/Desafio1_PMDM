package dao

import modelo.Nave

interface NaveDAO {
    fun insertar(nave: Nave): Boolean
    fun eliminar(matricula: String): Boolean

    fun obtenerNaves(): List<Nave>
    fun obtenerNavesPorTipo(tipo:String): List<Nave>
    fun obtenerNavePorMatricula(matricula: String): Nave?
}