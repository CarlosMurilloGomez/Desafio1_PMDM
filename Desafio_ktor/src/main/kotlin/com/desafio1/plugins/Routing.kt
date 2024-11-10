package com.desafio1.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rutas.rutasMision
import rutas.rutasNave
import rutas.rutasUsuario

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "Servidor funcionando")
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
        rutasUsuario()
        rutasNave()
        rutasMision()
    }
}
