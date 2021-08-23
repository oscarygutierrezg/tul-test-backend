package co.com.tul.test.ecommerce.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import co.com.tul.test.ecommerce.EcommerceApplication
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpEntity
import co.com.tul.test.ecommerce.dto.model.LinkDTO
import javax.annotation.PostConstruct
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption
import org.springframework.web.client.RestTemplate
import java.util.Collections
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.json.JSONObject
import org.springframework.http.MediaType
import java.util.Arrays
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import com.fasterxml.jackson.databind.ObjectMapper
import co.com.tul.test.ecommerce.dto.model.ProductDTO
import co.com.tul.test.ecommerce.exception.model.CustomErrorResponse
import co.com.tul.test.ecommerce.persistence.model.ProductCartResDTO

@SpringBootTest(
  classes = arrayOf(EcommerceApplication::class),
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductCartControllerTests() {
	
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate


	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND() {
		val result = testRestTemplate.getForEntity("/productCartByProduct/de93b9aa-150e-42ee-853f-df8625f34146",CustomErrorResponse::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, result!!.statusCode) },
                Executable { Assertions.assertEquals(404, result.body!!.status) },
                Executable { Assertions.assertEquals("No matching product was found", result.body!!.error) }
	
        )
	}
	
	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND1() {
		val result = testRestTemplate.getForEntity("/productCartByCart/de93b9aa-150e-42ee-853f-df8625f34146",CustomErrorResponse::class.java)
				Assertions.assertAll(
						Executable { Assertions.assertNotNull(result) },
						Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, result!!.statusCode) },
						Executable { Assertions.assertEquals(404, result.body!!.status) },
						Executable { Assertions.assertEquals("No matching cart was found", result.body!!.error) }
						
						)
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHref() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		var targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		var  request =  HttpEntity(targetRequest, headers);
		var result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		targetRequest = mapper.createObjectNode()

		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")

		 request =  HttpEntity(targetRequest, headers);
		val result1 = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result1) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result1?.statusCode) },
              Executable { Assertions.assertNotNull(result1?.body?.href)
				 }
        )
		
		targetRequest = mapper.createObjectNode()

		
		targetRequest.put("cartId", result1.body!!.href!!.split("/")!![5])
		targetRequest.put("productId", result.body!!.href!!.split("/")!![5])
		
		targetRequest.put("cantidad", 10)
		
		
		request =  HttpEntity(targetRequest, headers);
		val result2 = testRestTemplate.postForEntity("/productCart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result2?.statusCode) },
              Executable { Assertions.assertNotNull(result2?.body?.href)
				 }
        )
		
		val result3 = testRestTemplate.getForEntity(result2?.body?.href,ProductCartResDTO::class.java)
		var productCartResDTO = result3.body
	
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result3) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result3?.statusCode) },
              Executable { Assertions.assertEquals(targetRequest.get("cantidad").asInt(), productCartResDTO.cantidad) },
				 
        )
		
		
		val result4 = testRestTemplate.getForEntity("/productCartByProduct/"+result.body!!.href!!.split("/")!![5],Any::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result4) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result4!!.statusCode) },
        )
	
		val result5 = testRestTemplate.getForEntity("/productCartByCart/"+result1.body!!.href!!.split("/")!![5],Any::class.java)
				Assertions.assertAll(
						Executable { Assertions.assertNotNull(result5) },
						Executable { Assertions.assertEquals(HttpStatus.OK, result5!!.statusCode) },
						)
		
		testRestTemplate.delete(result2.body.href)
		testRestTemplate.delete(result.body.href)
		testRestTemplate.delete(result1.body.href)

	}
	
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHref1() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		var targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		var  request =  HttpEntity(targetRequest, headers);
		var result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		targetRequest = mapper.createObjectNode()

		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")

		 request =  HttpEntity(targetRequest, headers);
		val result1 = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result1) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result1?.statusCode) },
              Executable { Assertions.assertNotNull(result1?.body?.href)
				 }
        )
		
		targetRequest = mapper.createObjectNode()

		
		targetRequest.put("cartId", result1.body!!.href!!.split("/")!![5])
		targetRequest.put("productId", result.body!!.href!!.split("/")!![5])
		
		targetRequest.put("cantidad", 10)
		
		
		request =  HttpEntity(targetRequest, headers);
		val result2 = testRestTemplate.postForEntity("/productCart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result2?.statusCode) },
              Executable { Assertions.assertNotNull(result2?.body?.href)
				 }
        )
		
		
		
		testRestTemplate.delete(result2.body.href)
		testRestTemplate.delete(result.body.href)
		testRestTemplate.delete(result1.body.href)

	}
	
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHref11() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		var targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		var  request =  HttpEntity(targetRequest, headers);
		var result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		targetRequest = mapper.createObjectNode()

		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")

		 request =  HttpEntity(targetRequest, headers);
		val result1 = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result1) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result1?.statusCode) },
              Executable { Assertions.assertNotNull(result1?.body?.href)
				 }
        )
		
		targetRequest = mapper.createObjectNode()

		
		targetRequest.put("cartId", result1.body!!.href!!.split("/")!![5])
		targetRequest.put("productId", result.body!!.href!!.split("/")!![5])
		
		targetRequest.put("cantidad", 10)
		
		
		request =  HttpEntity(targetRequest, headers);
		val result2 = testRestTemplate.postForEntity("/productCart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result2?.statusCode) },
              Executable { Assertions.assertNotNull(result2?.body?.href)
				 }
        )
		
		targetRequest.put("cantidad", 9)
		request =  HttpEntity(targetRequest, headers);
		testRestTemplate.put("/productCart",request)
	
		val result4 = testRestTemplate.getForEntity(result2?.body?.href,ProductCartResDTO::class.java)
		val productDTO = result4.body
	
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result4) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result4?.statusCode) },
              Executable { Assertions.assertEquals(productDTO.cantidad, 9) },
				 
        )
		
		
		
	
		
		testRestTemplate.delete(result2.body.href)
		testRestTemplate.delete(result.body.href)
		testRestTemplate.delete(result1.body.href)

	}

	
	

}