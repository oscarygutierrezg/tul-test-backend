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
import org.mockito.InjectMocks
import org.mockito.BDDMockito.given

import co.com.tul.test.ecommerce.persistence.service.ProductService
import co.com.tul.test.ecommerce.persistence.service.ProductServiceImpl
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import co.com.tul.test.ecommerce.persistence.repository.ProductRepository
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.boot.test.context.TestConfiguration
import org.mockito.Mock

@SpringBootTest(
  classes = arrayOf(EcommerceApplication::class),
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest @Autowired constructor() {
	
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate
	
	@Mock
    lateinit var productRepository: ProductRepository
    @InjectMocks
    private lateinit var productService: ProductServiceImpl
	

	@Test
	fun whenCalled_thenShouldReturnEmptyArray() {
		val result = testRestTemplate.getForEntity("/product", String::class.java)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result?.statusCode) },
                Executable { Assertions.assertEquals("[]", result?.body) }
        )
	}

	
	@Test
	fun whenCalled_thenShouldReturnNotNullHref() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		val  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
	}
	
	
	
	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND() {
		val result = testRestTemplate.getForEntity("/product/de93b9aa-150e-42ee-853f-df8625f34146",CustomErrorResponse::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, result!!.statusCode) },
                Executable { Assertions.assertEquals(404, result.body!!.status) },
                Executable { Assertions.assertEquals("No matching product was found", result.body!!.error) }
	
        )
	}
	
	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND1() {
		try{
		testRestTemplate.delete("/product/de93b9aa-150e-42ee-853f-df8625f34146")
		}catch (ex: Exception){
			ex.printStackTrace()
		}
	}
	
	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND2() {
		try{
		testRestTemplate.put("/product/de93b9aa-150e-42ee-853f-df8625f34146", {})
		}catch (ex: Exception){
			ex.printStackTrace()
		}
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHrefAndGet() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("porcentajeDescuento", 0)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		val  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		val result1 = testRestTemplate.getForEntity(result?.body?.href,ProductDTO::class.java)
		val productDTO = result1.body
	
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result1?.statusCode) },
              Executable { Assertions.assertEquals(targetRequest.get("nombre").asText(), productDTO.nombre) },
				 
        )
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNull400() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Test")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "")
		targetRequest.put("precio", 100)
		targetRequest.put("porcentajeDescuento", 0)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		val  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/product", request,CustomErrorResponse::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode) },
              Executable { Assertions.assertEquals("Validation Failed descripcion no debe estar vacío ",result?.body?.error)
				 }
        )
		
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHrefAndUpdate() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		var  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		targetRequest.put("precio", 50)
		request =  HttpEntity(targetRequest, headers);
		
		
		testRestTemplate.put(result?.body?.href,request)
		
		
		val result2 = testRestTemplate.getForEntity(result?.body?.href,ProductDTO::class.java)
		val productDTO = result2.body
	
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result2?.statusCode) },
              Executable { Assertions.assertEquals(productDTO.precio, 50.0) },
				 
        )
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHrefAndDelete() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("sku", "12345566")
		targetRequest.put("descripcion", "Test")
		targetRequest.put("precio", 100)
		targetRequest.put("nombre", "Tuercas")
		targetRequest.put("tipoProducto", "WITHOUT_DISCOUNT")
		
		var  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/product", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		
		testRestTemplate.delete(result?.body?.href)
		
		
		val result2 = testRestTemplate.getForEntity("/product", String::class.java)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result2?.statusCode) },
                Executable { Assertions.assertEquals("[]", result2?.body) }
        )
	}

}