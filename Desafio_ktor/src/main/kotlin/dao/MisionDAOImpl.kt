package dao

import modelo.*

class MisionDAOImpl:MisionDAO {
    override fun insertarMision(mision: Mision): Boolean {
        val sql = "INSERT INTO mision (nombre, exp, naveAsig, tipo) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, mision.nombre)
            statement.setInt(2, mision.exp)
            statement.setInt(3, mision.naveAsig)
            statement.setInt(4, mision.tipo)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarVuelo(vuelo: MisionVuelo): Boolean {
        val sql = "INSERT INTO vuelo VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, vuelo.idMision)
            statement.setInt(2, vuelo.duracion)
            statement.setInt(3, vuelo.carga)
            statement.setInt(4, vuelo.pasajeros)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarBombardeo(bombardeo: MisionBombardeo): Boolean {
        val sql = "INSERT INTO bomardeos VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, bombardeo.idMision)
            statement.setInt(2, bombardeo.objetivos)
            statement.setInt(3, bombardeo.carga)
            statement.setInt(4, bombardeo.pasajeros)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarCaza(caza: MisionCaza): Boolean {
        val sql = "INSERT INTO caza VALUES (?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, caza.idMision)
            statement.setInt(2, caza.objetivos)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(idMision: Int): Boolean {
        val sql = "DELETE FROM mision WHERE id=?;"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerMisiones(): List<Mision> {
        val misiones = mutableListOf<Mision>()
        val sql = "SELECT * FROM mision"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val mision = Mision(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    exp = resultSet.getInt("exp"),
                    naveAsig = resultSet.getInt("naveAsig"),
                    tipo = resultSet.getInt("tipo")
                )
                misiones.add(mision)
            }
        }
        return misiones
    }

    override fun obtenerMisionPorId(idMision: Int): Mision? {
        val sql = "SELECT * FROM mision WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val mision = Mision(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    exp = resultSet.getInt("exp"),
                    naveAsig = resultSet.getInt("naveAsig"),
                    tipo = resultSet.getInt("tipo")
                )
                return mision
            }
        }
        return null
    }

    override fun obtenerVueloPorId(idMision: Int): MisionVuelo? {
        val sql = "SELECT * FROM vuelo WHERE idMision=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val vuelo = MisionVuelo(
                    idMision = resultSet.getInt("idMision"),
                    duracion = resultSet.getInt("duracion"),
                    carga = resultSet.getInt("carga"),
                    pasajeros = resultSet.getInt("pasajeros")
                )
                return vuelo
            }
        }
        return null
    }

    override fun obtenerBombardeoPorId(idMision: Int): MisionBombardeo? {
        val sql = "SELECT * FROM bombardeos WHERE idMision=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val bombardeo = MisionBombardeo(
                    idMision = resultSet.getInt("idMision"),
                    objetivos = resultSet.getInt("objetivos"),
                    carga = resultSet.getInt("carga"),
                    pasajeros = resultSet.getInt("pasajeros")
                )
                return bombardeo
            }
        }
        return null
    }

    override fun obtenerCazaPorId(idMision: Int): MisionCaza? {
        val sql = "SELECT * FROM caza WHERE idMision=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val caza = MisionCaza(
                    idMision = resultSet.getInt("idMision"),
                    objetivos = resultSet.getInt("objetivos")
                )
                return caza
            }
        }
        return null
    }

    override fun asignarMision(asignacion: Asignacion): Boolean {
        val sql = "INSERT INTO misionasignacion (idMision, idUsuario, estado) VALUES (?, ?, 0)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, asignacion.idMision)
            statement.setInt(2, asignacion.idUsuario)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerAsignacionesPorIdUsuario(idUsuario: Int): List<Asignacion> {
        val asignaciones = mutableListOf<Asignacion>()
        val sql = "SELECT * FROM misionasignacion WHERE idUsuario=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idUsuario)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val asignacion = Asignacion(
                    id = resultSet.getInt("id"),
                    idMision = resultSet.getInt("idMision"),
                    idUsuario = resultSet.getInt("idUsuario"),
                    estado = resultSet.getInt("estado")
                )
                asignaciones.add(asignacion)
            }
        }
        return asignaciones
    }

    override fun obtenerAsignacionPorId(idAsig: Int): Asignacion? {
        val sql = "SELECT * FROM misionasignacion WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idAsig)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val asignacion = Asignacion(
                    id = resultSet.getInt("id"),
                    idMision = resultSet.getInt("idMision"),
                    idUsuario = resultSet.getInt("idUsuario"),
                    estado = resultSet.getInt("estado")
                )
                return asignacion
            }
        }
        return null
    }
    override fun actualizarEstado(estado: Int, idAsignacion: Int): Boolean {
        val sql = "UPDATE misionasignacion SET estado=? WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, estado)
            statement.setInt(2, idAsignacion)

            return statement.executeUpdate() > 0
        }
        return false
    }
}