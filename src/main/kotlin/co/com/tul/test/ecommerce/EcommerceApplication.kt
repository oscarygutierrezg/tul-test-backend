package co.com.tul.test.ecommerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.swagger.v3.oas.annotations.OpenAPIDefinition

@SpringBootApplication
class EcommerceApplication

fun main(args: Array<String>) {
	runApplication<EcommerceApplication>(*args)
}