package co.com.tul.test.ecommerce.dto.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class LinksDTO (
	@JsonProperty("all-products")
	@Schema(description = "Recurso para obtener todos los productos.", example = "http://localhost:8080/ecommerce/product")	
    val  allProducts:HrefDTO?,
	@Schema(description = "Recurso para obtener el mismo producto.", example = "http://localhost:8080/ecommerce/product/ced4e507-6fff-46f2-b722-59e497f30f05")	
    val self: HrefDTO?
)