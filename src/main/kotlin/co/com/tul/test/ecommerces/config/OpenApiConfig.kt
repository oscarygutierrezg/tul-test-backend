package co.com.tul.test.ecommerces.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components
import org.springframework.context.annotation.Bean

class OpenApiConfig {
	@Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .components(Components())
                .info(Info()
                        .title("My Fantastic API")
                        .description("This is the API documentation for my fantastic awesome incredible software."))
    }
}