package rutas

import dao.UsuarioDAO
import dao.UsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Usuario
import modelo.UsuarioLogIn
import modelo.UsuarioPerfil


val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

fun Route.rutasUsuario(){
    route("/registrarUsuario") {
        post{
            val user = call.receive<Usuario>()

            if (!usuarioDAO.insertar(user)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/modificarPerfilUsuario") {
        put {
            val user = call.receive<UsuarioPerfil>()
            val usuario = usuarioDAO.obtenerUsuarioPorId(user.id) ?: return@put call.respond(HttpStatusCode.NotFound, false)
            if (!usuarioDAO.actualizarPerfil(user)) {
                return@put call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }
    }
    route("/modificarExperienciaUsuario") {
        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, false)
            val experiencia = call.receive<Int>()
            val usuario = usuarioDAO.obtenerUsuarioPorId(id.toInt()) ?: return@put call.respond(HttpStatusCode.NotFound, false)
            if (!usuarioDAO.actualizarExperiencia(experiencia, id.toInt())) {
                return@put call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }
    }

    route("/eliminarUsuario") {
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val usuario = usuarioDAO.obtenerUsuarioPorId(id.toInt()) ?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!usuarioDAO.eliminar(id.toInt())){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }
    route("/login") {
        get{
            val user = call.receive<UsuarioLogIn>()
            val usuario = usuarioDAO.obtenerUsuarioPorNombre(user.nombre) ?: return@get call.respond(HttpStatusCode.NotFound, null)
            if (usuario.password != user.password){
                return@get call.respond(HttpStatusCode.BadRequest, null)
            }
            call.respond(HttpStatusCode.OK, usuario)
        }
    }
    route("/usuarios") {
        get {
            if (usuarioDAO.obtenerUsuarios().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.obtenerUsuarios())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val usuario = usuarioDAO.obtenerUsuarioPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, usuario)
        }
    }
    route("/pilotos") {
        get {
            if (usuarioDAO.obtenerPilotos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.obtenerPilotos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
    }
    route("/ranking") {
        get {
            if (usuarioDAO.obtenerRankingPilotos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.obtenerRankingPilotos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
    }

    route("/rol") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val rol = usuarioDAO.obtenerRolPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, rol)
        }
    }

    route("/nivel") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val nivel = usuarioDAO.obtenerRolPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, nivel)
        }
    }
}