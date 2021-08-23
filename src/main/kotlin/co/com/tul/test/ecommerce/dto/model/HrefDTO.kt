package co.com.tul.test.ecommerce.dto.model

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.Column
import java.util.UUID
import javax.validation.constraints.NotNull
import co.com.tul.test.ecommerce.validation.NumberNotNegative
import co.com.tul.test.ecommerce.validation.NumberNotNull
import javax.validation.constraints.NotEmpty
import io.swagger.v3.oas.annotations.media.Schema

data class HrefDTO (
		@Schema(description = "Url para obener el recurso asociado a un producto.", example = "http://localhost:8080/ecommerce/product/db36cd7e-42a5-47c5-9b03-1942f6153e32")
		val  href: String?
		) {
}
