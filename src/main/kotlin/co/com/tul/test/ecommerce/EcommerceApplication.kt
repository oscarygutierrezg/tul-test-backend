package co.com.tul.test.ecommerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.databind.ObjectMapper

@SpringBootApplication
class EcommerceApplication

fun main(args: Array<String>) {
	runApplication<EcommerceApplication>(*args)
//	val  mapper =  ObjectMapper()
//val rootNode = mapper.createObjectNode()
//
//		rootNode.put("nombre", "Tuercas")
//		rootNode.put("sku", "12345566")
//		rootNode.put("descripcion", "Test")
//		rootNode.put("nombre", "Tuercas")
//		rootNode.put("tipoProducto", "WITHOUT_DISCOUNT")
//		
//	 RestTemplate().postForLocation("/product", rootNode)
}