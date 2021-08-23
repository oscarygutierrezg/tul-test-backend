package co.com.tul.test.ecommerce.dto.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class LinksDTO2 (
    @JsonProperty("all-productscart-by-product") 
    val  allProductscartByProduct: HrefDTO?,
    @JsonProperty("all-productscart-by-cart") 
    val  allProductscartByCart: HrefDTO?,
    val  self: HrefDTO?
)