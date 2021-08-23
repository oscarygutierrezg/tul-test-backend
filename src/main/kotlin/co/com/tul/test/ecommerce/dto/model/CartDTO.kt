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
import co.com.tul.test.ecommerce.persistence.model.CartStatusType

data class CartDTO (
	
	@Schema(description = "Id del carrito de compras de compras.", example = "a4731748-a79f-4b9d-970e-0417653fec5b")	
	val id: String,
	@Schema(description = "Descuento del carrito de compras de compras.", example = "Tuerca")	
	val descuento: Float,
	@Schema(description = "Total del carrito de compras de compras.", example = "Tuerca")	
	val total: Float,
	@Schema(description = "Esatdi del carrito de compras de compras.", example = "Tuerca")	
	val estado: CartStatusType?,
    @Schema(description = "Links asociados a los recursos carrito de compras.")
	@JsonProperty("_links")
	val  links: LinksDTO) 