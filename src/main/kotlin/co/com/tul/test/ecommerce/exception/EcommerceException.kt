package co.com.tul.test.ecommerce.exception

import org.springframework.http.HttpStatus

open class EcommerceException(val statusCode: HttpStatus, val reason: String) : Exception()