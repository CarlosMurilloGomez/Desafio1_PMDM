package rutas

import dao.NaveDAO
import dao.NaveDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Nave

val naveDAO: NaveDAO = NaveDAOImpl()

fun Route.rutasNave() {
    route("/registrarNave") {
        post {
            val naveBody = call.receive<Nave>()
            val nave = naveDAO.obtenerNavePorMatricula(naveBody.matricula)
            if (nave != null) return@post call.respond(HttpStatusCode.BadRequest, false)

            if (!naveDAO.insertar(naveBody)) {
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }
    route("/eliminarNave") {
        delete("{matricula?}") {
            val matricula = call.parameters["matricula"] ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val nave = naveDAO.obtenerNavePorMatricula(matricula) ?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!naveDAO.eliminar(matricula)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }

    route("/naves") {
        get {
            return@get call.respond(HttpStatusCode.OK, naveDAO.obtenerNaves())
        }
        get("{matricula?}") {
            val matricula = call.parameters["matricula"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val nave = naveDAO.obtenerNavePorMatricula(matricula) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, nave)
        }
    }

    route("/navesPorTipo") {
        get("{tipo?}") {
            val tipo = call.parameters["tipo"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            return@get call.respond(HttpStatusCode.OK, naveDAO.obtenerNavesPorTipo(tipo))
        }
    }
}