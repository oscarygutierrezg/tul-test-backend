package co.com.tul.test.ecommerce.persistence.model

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import java.time.LocalDate
import java.util.UUID
import org.hibernate.annotations.GenericGenerator
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.OneToMany
import co.com.tul.test.ecommerce.validation.FloatNumberNotNegative
import co.com.tul.test.ecommerce.validation.FloatNumberNotNull
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name = "Cart")
data class Cart (
		@Id
		@GeneratedValue(generator = "UUID")
		@GenericGenerator(
				name = "UUID",
				strategy = "org.hibernate.id.UUIDGenerator",
				)
		@Column(name = "id", updatable = false, nullable = false)
		val id: UUID? = UUID.randomUUID(),
		@Column(name = "descuento", nullable = false)
		@field:FloatNumberNotNegative
		val descuento: Float,
		@Column(name = "total", nullable = false)
		@field:FloatNumberNotNegative
		val total: Float,
		@Column(name = "estado", nullable = false)
		@field:NotNull
		val estado: CartStatusType?,
		@OneToMany(mappedBy = "product")
		@JsonIgnore
		val cartProduct: List<ProductCart>?
		){
	private constructor(): this(null,0F,0F,null,null)
}
