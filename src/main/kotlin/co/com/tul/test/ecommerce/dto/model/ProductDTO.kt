package co.com.tul.test.ecommerce.dto.model

import java.util.UUID
import javax.validation.constraints.NotNull
import co.com.tul.test.ecommerce.validation.NumberNotNegative
import co.com.tul.test.ecommerce.validation.NumberNotNull
import javax.validation.constraints.NotEmpty
import io.swagger.v3.oas.annotations.media.Schema
import co.com.tul.test.ecommerce.persistence.model.ProductType
import co.com.tul.test.ecommerce.persistence.model.ProductCart
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductDTO (
	
	@Schema(description = "Id del producto de compras.", example = "a4731748-a79f-4b9d-970e-0417653fec5b")	
	val id: String,
	@Schema(description = "Nombre del producto de compras.", example = "Tuerca")	
    val nombre: String,
	@Schema(description = "Sku del producto de compras.", example = "12324731748")	
    val sku: String,
	@Schema(description = "Descripcion del producto de compras.", example = "Tuerca de muy bnuena calidad")	
    val descripcion: String,
	@Schema(description = "Precio del producto de compras.", example = "100.0")	
    val precio: Double,
	@Schema(description = "TipoProducto del producto de compras.", example = "WITH_DISCOUNT")	
    val tipoProducto: ProductType,
    @Schema(description = "Links asociados a los recursos producto.")
	@JsonProperty("_links")
	val  links: LinksDTO) 