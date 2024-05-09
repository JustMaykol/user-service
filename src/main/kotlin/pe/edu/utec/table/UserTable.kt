package pe.edu.utec.table

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
	val id = varchar("id", 255)
	val name = varchar("name", 255)
	val password = varchar("password", 255)

	val money = integer("money")
	val admin = bool("admin")
}