package co.com.tul.test.ecommerce.dto.model

import java.util.UUID
import javax.validation.constraints.NotNull
import co.com.tul.test.ecommerce.validation.NumberNotNegative
import co.com.tul.test.ecommerce.validation.NumberNotNull
import javax.validation.constraints.NotEmpty
import io.swagger.v3.oas.annotations.media.Schema

data class ProductCartDTO (
		@Schema(description = "Id del carrito de compras.", example = "a4731748-a79f-4b9d-970e-0417653fec5b")
		@field:NotNull
		val  cartId: UUID?,
		@field:NotNull
		@Schema(description = "Id del carrito de producto.", example = "a4731748-a79f-4b9d-970e-0417653fec5b")
		val   productId: UUID?,
		@field:NotNull
		@field:NumberNotNegative
		@field:NumberNotNull
		@Schema(description = "Cantidad a comprar.", example = "10")
		val cantidad: Int,
		) 