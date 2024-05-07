package pe.edu.utec.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
	val name: String,
	val password: String
)
