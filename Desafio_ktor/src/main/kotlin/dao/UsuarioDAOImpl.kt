package dao

import modelo.Usuario
import modelo.UsuarioPerfil

class UsuarioDAOImpl:UsuarioDAO {
    override fun insertar(usuario: Usuario): Boolean {
        val sql = "INSERT INTO usuario (nombre, password, activo, foto, edad, experiencia, rol) VALUES(?, ?, 0, NULL, ?, ?, ?)"
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
        val sql = "UPDATE usuario SET nombre=?, password=?, foto=? WHERE id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, perfil.nombre)
            statement.setString(2, perfil.password)
            statement.setString(3, perfil.foto)
            statement.setInt(4, perfil.id)

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
        val sql = "DELETE FROM usuarios WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerUsuarios(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM usuarios"
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

    override fun obtenerPilotos(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM usuarios WHERE rol=2"
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
        val sql = "SELECT * FROM usuarios WHERE rol=2 ORDER BY experiencia DESC"
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
        val sql = "SELECT * FROM usuarios WHERE id = ?"
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
        val sql = "SELECT * FROM usuarios WHERE LOWER(nombre) = LOWER(?)"
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

    override fun obtenerRolPorId(id: Int): String? {
        val sql = "SELECT LOWER(rol.descripcion) AS nombreRol FROM rol JOIN usuario ON rol.id=usuario.rol WHERE usuario.id=?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.getString("nombreRol")
            }
        }
        return null
    }

    override fun obtenerNivelPorId(id: Int): String? {
        val sql = "SELECT LOWER(descripcion) AS nivel FROM experiencia WHERE limiteBajo<=(SELECT experiencia FROM usuario WHERE id = ?) AND limiteAlto>=(SELECT experiencia FROM usuario WHERE id = ?);"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            statement.setInt(2, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.getString("nivel")
            }
        }
        return null
    }
}