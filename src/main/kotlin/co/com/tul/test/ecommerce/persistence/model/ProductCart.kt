package co.com.tul.test.ecommerce.persistence.model

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.Column
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.EmbeddedId
import co.com.tul.test.ecommerce.persistence.model.pk.ProductCartPK
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.JoinColumn

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import co.com.tul.test.ecommerce.validation.NumberNotNegative
import co.com.tul.test.ecommerce.validation.NumberNotNull
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name = "product_cart")
data class ProductCart(
		@EmbeddedId
		@JsonIgnore
		val id:  ProductCartPK? ,
		@ManyToOne
		@MapsId("cardId")
		@JoinColumn(name = "card_id")
		val cart: Cart?,
		@ManyToOne
		@MapsId("productId")
		@JoinColumn(name = "product_id")
		val product: Product?,
		@Column(name = "cantidad", nullable = false)
		@field:NotNull
		@field:NumberNotNegative
		@field:NumberNotNull
		var cantidad: Int,
		) : Serializable {
			private constructor(): this(null,null,null,0)
		}

