package dao

import modelo.Cadena
import modelo.Nave
import modelo.Tipo

interface NaveDAO {
    fun insertar(nave: Nave): Boolean
    fun eliminar(matricula: String): Boolean

    fun obtenerNaves(): List<Nave>
    fun obtenerNavesPorTipo(tipo:String): List<Nave>
    fun obtenerNavePorMatricula(matricula: String): Nave?
    fun obtenerTiposNaves(): List<Tipo>
}