package pe.edu.utec.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pe.edu.utec.repository.UserRepository
import pe.edu.utec.request.UserRequest

fun Routing.userRoute(
	repository: UserRepository
) {

	route("/user") {

		post {
			val userRequest = try {
				call.receive<UserRequest>()
			} catch (exception: Exception) {
				call.respond(HttpStatusCode.BadRequest)
				return@post
			}

			if (userRequest.name.isBlank() || userRequest.password.isBlank()) {
				call.respond(HttpStatusCode.BadRequest)
				return@post
			}

			if (repository.readByName(userRequest.name) != null) {
				call.respond(HttpStatusCode.Conflict)
				return@post
			}

			repository.create(userRequest)
			call.respond(HttpStatusCode.Created, userRequest)
		}

		get("/{id}") {
			val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)

			repository.readById(id)?.let { user ->
				call.respond(user)
			} ?: call.respond(HttpStatusCode.NotFound)
		}

		put("/{id}") {
			val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
			val user = call.receive<UserRequest>()

			if (repository.update(id, user)) {
				call.respond(HttpStatusCode.OK)
			} else {
				call.respond(HttpStatusCode.NotFound)
			}
		}

		delete("/{id}") {
			val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

			if (repository.delete(id)) {
				call.respond(HttpStatusCode.OK)
			} else {
				call.respond(HttpStatusCode.NotFound)
			}
		}

	}

}