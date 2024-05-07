package pe.edu.utec.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
	val name: String,
	val password: String
)
