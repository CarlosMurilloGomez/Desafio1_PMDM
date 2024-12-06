package dao

import modelo.Cadena
import modelo.Nave
import modelo.Tipo
import modelo.TipoCargaPasajeros

interface NaveDAO {
    fun insertar(nave: Nave): Boolean
    fun eliminar(matricula: String): Boolean

    fun obtenerNaves(): List<Nave>
    fun obtenerNavesPorTipoCargaPasajeros(datos:TipoCargaPasajeros): List<Nave>
    fun obtenerNavePorMatricula(matricula: String): Nave?
    fun obtenerTiposNaves(): List<Tipo>
}