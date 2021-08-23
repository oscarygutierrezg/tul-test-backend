package co.com.tul.test.ecommerce.persistence.model.pk

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.Column
import java.util.UUID

@Embeddable
data class ProductCartPK (
		@Column(name = "card_id")
		val  cardid: UUID?,
		@Column(name = "product_id")
		val   productid: UUID?
		) : Serializable{
	private constructor(): this(null,null)
}
