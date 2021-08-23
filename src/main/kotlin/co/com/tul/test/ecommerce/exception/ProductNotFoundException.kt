package co.com.tul.test.ecommerce.exception

import org.springframework.http.HttpStatus

class ProductNotFoundException( statusCode: HttpStatus,  reason: String) : EcommerceException(statusCode,reason)