package pe.edu.utec.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import pe.edu.utec.model.UserModel
import pe.edu.utec.request.UserRequest
import pe.edu.utec.table.UserTable
import java.util.*

class UserRepository {

	fun create(user: UserRequest): Unit = transaction {
		UserTable.insert {
			it[id] = UUID.randomUUID().toString()
			it[name] = user.name
			it[password] = user.password
		}
	}

	fun readById(id: String): UserModel? = transaction {
		UserTable.select { UserTable.id eq id }.mapNotNull { toUser(it) }.singleOrNull()
	}

	fun readByName(name: String): UserModel? = transaction {
		UserTable.select { UserTable.name eq name }.mapNotNull { toUser(it) }.singleOrNull()
	}

	fun update(id: String, user: UserRequest): Boolean = transaction {
		UserTable.update({ UserTable.id eq id }) {
			it[name] = user.name
			it[password] = user.password
		} > 0
	}

	fun delete(id: String): Boolean = transaction {
		UserTable.deleteWhere { UserTable.id eq id } > 0
	}

	private fun toUser(row: ResultRow): UserModel =
		UserModel(
			id = row[UserTable.id],
			name = row[UserTable.name],
			password = row[UserTable.password]
		)

}