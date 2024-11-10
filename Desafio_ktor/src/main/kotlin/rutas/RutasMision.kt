package rutas

import dao.MisionDAO
import dao.MisionDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.*

val misionDAO: MisionDAO = MisionDAOImpl()

fun Route.rutasMision() {
    route("/registrarMision") {
        post {
            val misionBody = call.receive<Mision>()
            val mision = misionDAO.obtenerMisionPorId(misionBody.id)
            if (mision != null) return@post call.respond(HttpStatusCode.BadRequest, false)

            if (!misionDAO.insertarMision(misionBody)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }
    route("/registrarVuelo") {
        post {
            val misionBody = call.receive<MisionVuelo>()
            val mision = misionDAO.obtenerVueloPorId(misionBody.idMision)
            if (mision != null) return@post call.respond(HttpStatusCode.BadRequest, false)

            if (!misionDAO.insertarVuelo(misionBody)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }
    route("/registrarBombardeo") {
        post {
            val misionBody = call.receive<MisionBombardeo>()
            val mision = misionDAO.obtenerBombardeoPorId(misionBody.idMision)
            if (mision != null) return@post call.respond(HttpStatusCode.BadRequest, false)

            if (!misionDAO.insertarBombardeo(misionBody)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }
    route("/registrarCaza") {
        post {
            val misionBody = call.receive<MisionCaza>()
            val mision = misionDAO.obtenerCazaPorId(misionBody.idMision)
            if (mision != null) return@post call.respond(HttpStatusCode.BadRequest, false)

            if (!misionDAO.insertarCaza(misionBody)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/eliminarMision") {
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val mision = misionDAO.obtenerMisionPorId(id.toInt()) ?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!misionDAO.eliminar(id.toInt())) {
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }

    route("/misiones") {
        get {
            return@get call.respond(HttpStatusCode.OK, misionDAO.obtenerMisiones())
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val mision = misionDAO.obtenerMisionPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, mision)
        }
    }

    route("/vuelo") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val mision = misionDAO.obtenerVueloPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, mision)
        }
    }

    route("/bombardeo") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val mision = misionDAO.obtenerBombardeoPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, mision)
        }
    }

    route("/caza") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val mision = misionDAO.obtenerCazaPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, mision)
        }
    }


    route("/asignarMision") {
        post {
            val asignacionBody = call.receive<Asignacion>()

            if (!misionDAO.asignarMision(asignacionBody)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/asigancionesPorUsuario") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            return@get call.respond(HttpStatusCode.OK, misionDAO.obtenerAsignacionesPorIdUsuario(id.toInt()))
        }
    }
    route("/asignacion") {
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val asignacion = misionDAO.obtenerAsignacionPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, asignacion)
        }
    }
    route("/actualizarEstado") {
        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, false)
            val estado = call.receive<Int>()
            val asignacion = misionDAO.obtenerAsignacionPorId(id.toInt()) ?: return@put call.respond(HttpStatusCode.NotFound, false)
            if (!misionDAO.actualizarEstado(estado, id.toInt())) {
                return@put call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }
    }
}