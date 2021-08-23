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

data class LinkProductCartDTO (
		@Schema(description = "Url para obener el recurso asociado a un producto.", example = "http://localhost:8080/ecommerce/productCart/3f6a18b2-e2b4-47c2-b9c8-b287e2de6e88/d7c01c1b-9013-4cf0-9355-94d4604b6731")
		val  href: String?
		) {
}
