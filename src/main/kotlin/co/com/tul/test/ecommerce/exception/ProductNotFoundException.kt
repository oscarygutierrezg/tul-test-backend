package co.com.tul.test.ecommerce.exception

import org.springframework.http.HttpStatus

class ProductNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()