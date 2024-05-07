package pe.edu.utec.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pe.edu.utec.model.LoginModel
import pe.edu.utec.repository.UserRepository

fun Routing.loginRoute(
	repository: UserRepository
) {

	route("/login") {

		post {
			val login = call.receive<LoginModel>()
			val user = repository.readByName(login.name) ?: return@post call.respond(HttpStatusCode.BadRequest)

			if (user.password != login.password) {
				call.respond(HttpStatusCode.BadRequest)
			} else {
				call.respond(user.id)
			}
		}

	}

}