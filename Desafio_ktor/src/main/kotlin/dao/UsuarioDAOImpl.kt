package dao

import modelo.*

class UsuarioDAOImpl:UsuarioDAO {
    override fun insertar(usuario: Usuario): Boolean {
        val sql = "INSERT INTO usuario (nombre, password, activo, foto, edad, experiencia, rol) VALUES(?, ?, 0, 'https://res.cloudinary.com/dxqrclhjs/image/upload/v1731946150/stormtrooperFotoPerfil_qv3hnw.jpg', ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.password)
            statement.setInt(3, usuario.edad)
            statement.setInt(4, usuario.experiencia)
            statement.setInt(5, usuario.rol)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun actualizarPerfil(perfil: UsuarioPerfil): Boolean {
        val sql = "UPDATE usuario SET password=?, foto=? WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, perfil.password)
            statement.setString(2, perfil.foto)
            statement.setInt(3, perfil.id)

            return statement.executeUpdate() > 0
        }
        return false
    }
    override fun activarCuenta(id: Int): Boolean {
        val sql = "UPDATE usuario SET activo=1 WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun actualizarExperiencia(expGanada: Int, id:Int): Boolean {
        val sql = "UPDATE usuario SET experiencia=experiencia+? WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, expGanada)
            statement.setInt(2, id)


            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(id: Int): Boolean {
        val sql = "DELETE FROM usuario WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }


    override fun obtenerPilotos(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM usuario WHERE rol=2"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val usuario = Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    activo = resultSet.getInt("activo"),
                    foto = resultSet.getString("foto"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    rol = resultSet.getInt("rol")
                )
                usuarios.add(usuario)
            }
        }
        return usuarios
    }

    override fun obtenerRankingPilotos(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM usuario WHERE rol=2 ORDER BY experiencia DESC"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val usuario = Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    activo = resultSet.getInt("activo"),
                    foto = resultSet.getString("foto"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    rol = resultSet.getInt("rol")
                )
                usuarios.add(usuario)
            }
        }
        return usuarios
    }
    override fun obtenerUsuarioPorId(id: Int): Usuario? {
        val sql = "SELECT * FROM usuario WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    activo = resultSet.getInt("activo"),
                    foto = resultSet.getString("foto"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    rol = resultSet.getInt("rol")
                )
            }
        }
        return null
    }

    override fun obtenerUsuarioPorNombre(nombre: String): Usuario? {
        val sql = "SELECT * FROM usuario WHERE LOWER(nombre) = LOWER(?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nombre)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    activo = resultSet.getInt("activo"),
                    foto = resultSet.getString("foto"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    rol = resultSet.getInt("rol")
                )
            }
        }
        return null
    }

    override fun obtenerRolPorId(id: Int): Cadena? {
        val sql = "SELECT rol.descripcion AS nombreRol FROM rol JOIN usuario ON rol.id=usuario.rol WHERE usuario.id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Cadena(resultSet.getString("nombreRol"))

            }
        }
        return null
    }

    override fun obtenerNivelPorId(id: Int): Cadena? {
        val sql = "SELECT descripcion AS nivel FROM experiencia WHERE limiteBajo<=(SELECT experiencia FROM usuario WHERE id = ?) AND limiteAlto>=(SELECT experiencia FROM usuario WHERE id = ?);"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            statement.setInt(2, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Cadena(resultSet.getString("nivel"))
            }
        }
        return null
    }

    override fun obtenerEstadisticasPorId(id: Int): Estadisticas? {
        val sql = "SELECT COALESCE(COUNT(CASE WHEN estado = 1 THEN 1 END), 0) AS misionesPendientes, " +
                "COALESCE(COUNT(CASE WHEN estado = 2 THEN 1 END), 0) AS misionesCompletadas, " +
                "COALESCE(COUNT(CASE WHEN estado = 3 THEN 1 END), 0) AS misionesFallidas " +
                "FROM misionasignacion WHERE idUsuario = ? GROUP BY idUsuario;"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Estadisticas(
                    idUsuario = id,
                    nivel = "",
                    misionesPendientes = resultSet.getInt("misionesPendientes"),
                    misionesCompletadas = resultSet.getInt("misionesCompletadas"),
                    misionesFallidas = resultSet.getInt("misionesFallidas")
                )
            }
        }
        return Estadisticas(
            idUsuario = id,
            nivel = "",
            misionesPendientes = 0,
            misionesCompletadas = 0,
            misionesFallidas = 0
        )
    }
}