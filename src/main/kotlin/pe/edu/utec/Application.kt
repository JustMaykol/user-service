package pe.edu.utec

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import pe.edu.utec.repository.UserRepository
import pe.edu.utec.route.loginRoute
import pe.edu.utec.route.userRoute
import pe.edu.utec.table.UserTable

fun main() {
	embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
		config()
		module()
	}.start(wait = true)
}

fun Application.config() {
	install(ContentNegotiation) {
		json()
	}
}

fun Application.module() {
	Database.connect(
		url = "jdbc:mysql://database-1.cfzyzra0pejl.us-east-1.rds.amazonaws.com:3306/kotlin",
		driver = "com.mysql.cj.jdbc.Driver",
		user = "admin",
		password = "CC-utec_2024-s3"
	)

	transaction {
		SchemaUtils.create(UserTable)
	}

	val repository = UserRepository()

	routing {
		userRoute(repository)
		loginRoute(repository)
	}

}