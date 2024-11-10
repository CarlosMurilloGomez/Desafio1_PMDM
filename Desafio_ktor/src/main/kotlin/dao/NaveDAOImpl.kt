package dao

import modelo.Nave

class NaveDAOImpl:NaveDAO {
    override fun insertar(nave: Nave): Boolean {
        val sql = "INSERT INTO nave VALUES(UPPER(?), ?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nave.matricula)
            statement.setString(2, nave.foto)
            statement.setInt(3, nave.tipo)
            statement.setInt(4, nave.carga)
            statement.setInt(5, nave.pasajeros)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(matricula: String): Boolean {
        val sql = "DELETE FROM nave WHERE UPPER(matricula)=UPPER(?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, matricula)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerNaves(): List<Nave> {
        val naves = mutableListOf<Nave>()
        val sql = "SELECT * FROM nave"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val nave = Nave(
                    matricula = resultSet.getString("matricula"),
                    foto = resultSet.getString("foto"),
                    carga = resultSet.getInt("carga"),
                    tipo = resultSet.getInt("tipo"),
                    pasajeros = resultSet.getInt("pasajeros")
                )
                naves.add(nave)
            }
        }
        return naves
    }

    override fun obtenerNavesPorTipo(tipo: String): List<Nave> {
        val naves = mutableListOf<Nave>()
        val sql = "SELECT nave.* FROM nave JOIN tiponave ON tiponave.id=nave.tipo WHERE LOWER(tiponave.descripcion) LIKE LOWER(?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, tipo)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val nave = Nave(
                    matricula = resultSet.getString("matricula"),
                    foto = resultSet.getString("foto"),
                    carga = resultSet.getInt("carga"),
                    tipo = resultSet.getInt("tipo"),
                    pasajeros = resultSet.getInt("pasajeros")
                )
                naves.add(nave)
            }
        }
        return naves
    }

    override fun obtenerNavePorMatricula(matricula: String): Nave? {
        val sql = "SELECT * FROM nave WHERE UPPER(matricula)=UPPER(?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, matricula)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val nave = Nave(
                    matricula = resultSet.getString("matricula"),
                    foto = resultSet.getString("foto"),
                    carga = resultSet.getInt("carga"),
                    tipo = resultSet.getInt("tipo"),
                    pasajeros = resultSet.getInt("pasajeros")
                )
                return nave
            }
        }
        return null
    }
}