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
import co.com.tul.test.ecommerce.validation.NumberNotNegative
import co.com.tul.test.ecommerce.validation.NumberNotNull

@Entity
@Table(name = "product")
data class Product (
		@Id
		@GeneratedValue(generator = "UUID")
		@GenericGenerator(
				name = "UUID",
				strategy = "org.hibernate.id.UUIDGenerator",
				)
		@Column(name = "id", updatable = false, nullable = false)
		val id: UUID? = UUID.randomUUID(),
		@Column(name = "nombre", unique = false, nullable = false)
		@field:NotNull
		@field:NotEmpty
		val nombre: String?,
		@Column(name = "sku", nullable = false)
		@field:NotNull
		@field:NotEmpty
		val sku: String?,
		@Column(name = "descripcion", nullable = false)
		@field:NotNull
		@field:NotEmpty
		val descripcion: String?,
		@Column(name = "precio", nullable = false)
		@field:NotNull
		@field:NumberNotNegative
		@field:NumberNotNull
		val precio: Float,
		@Column(name = "tipo_producto", nullable = false)
		@field:NotNull
		val tipoProducto: ProductType?,
		){
	private constructor(): this(null,null,null,null,0F,null)
}
