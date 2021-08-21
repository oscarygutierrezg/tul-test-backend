package co.com.tul.test.ecommerce.exception.model


data class CustomErrorResponse (
		val  timestamp: String,
		val  status: Int,
		val error: String,
		val path: String
		) 