package co.com.tul.test.ecommerce.persistence.model

import java.io.Serializable
import java.util.UUID
import co.com.tul.test.ecommerce.persistence.model.pk.ProductCartPK


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import co.com.tul.test.ecommerce.validation.NumberNotNegative
import co.com.tul.test.ecommerce.validation.NumberNotNull
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import co.com.tul.test.ecommerce.dto.model.LinksDTO2

data class ProductCartResDTO(
		val id:  ProductCartPK? ,
		val cart: Cart?,
		val product: Product?,
		var cantidad: Int,
		@JsonProperty("_links")
		val  links: LinksDTO2
		) 

