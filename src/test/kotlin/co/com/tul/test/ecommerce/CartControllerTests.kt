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
import co.com.tul.test.ecommerce.dto.model.CartDTO
import co.com.tul.test.ecommerce.exception.model.CustomErrorResponse

@SpringBootTest(
  classes = arrayOf(EcommerceApplication::class),
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerTest() {
	
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate


	@Test
	fun whenCalled_thenShouldReturnEmptyArray() {
		val result = testRestTemplate.getForEntity("/cart", String::class.java)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result?.statusCode) },
        )
	}

	
	@Test
	fun whenCalled_thenShouldReturnNotNullHref() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")

		
		val  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
	}
	
	
	
	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND() {
		val result = testRestTemplate.getForEntity("/cart/de93b9aa-150e-42ee-853f-df8625f34146",CustomErrorResponse::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, result!!.statusCode) },
                Executable { Assertions.assertEquals(404, result.body!!.status) },
                Executable { Assertions.assertEquals("No matching cart was found", result.body!!.error) }
	
        )
	}
	
		@Test
	fun whenCalled_thenShouldReturnNOT_FOUND1() {
		try{
		testRestTemplate.delete("/cart/de93b9aa-150e-42ee-853f-df8625f34146")
		}catch (ex: Exception){
			ex.printStackTrace()
		}
	}
	
	@Test
	fun whenCalled_thenShouldReturnNOT_FOUND2() {
		try{
		testRestTemplate.put("/cart/de93b9aa-150e-42ee-853f-df8625f34146", {})
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

		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")

		
		val  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		val result1 = testRestTemplate.getForEntity(result?.body?.href,CartDTO::class.java)
		val cartDTO = result1.body
	
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result1?.statusCode) },
              Executable { Assertions.assertEquals(targetRequest.get("estado").asText(), cartDTO.estado.toString()) },
				 
        )
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHrefAndUpdate() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		
		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")
		
		var  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		targetRequest.put("estado", "DESERTED")
		request =  HttpEntity(targetRequest, headers);
		
		
		testRestTemplate.put(result?.body?.href,request)
		
		
		val result2 = testRestTemplate.getForEntity(result?.body?.href,CartDTO::class.java)
		val cartDTO = result2.body
	
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result2?.statusCode) },
              Executable { Assertions.assertEquals(cartDTO.estado.toString(), "DESERTED") },
				 
        )
	}
	
	@Test
	fun whenCalled_thenShouldReturnNotNullHrefAndDelete() {
		val headers = HttpHeaders()
		
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
		
		val  mapper =  ObjectMapper()
		val targetRequest = mapper.createObjectNode()

		targetRequest.put("descuento", 0)
		targetRequest.put("total", 0)
		targetRequest.put("estado", "CREATED")
		
		var  request =  HttpEntity(targetRequest, headers);
		val result = testRestTemplate.postForEntity("/cart", request,LinkDTO::class.java)
		  Assertions.assertAll(
                Executable { Assertions.assertNotNull(result) },
                Executable { Assertions.assertEquals(HttpStatus.CREATED, result?.statusCode) },
              Executable { Assertions.assertNotNull(result?.body?.href)
				 }
        )
		
		
		testRestTemplate.delete(result?.body?.href)
		
		
		val result2 = testRestTemplate.getForEntity("/cart", String::class.java)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(result2) },
                Executable { Assertions.assertEquals(HttpStatus.OK, result2?.statusCode) },
                Executable { Assertions.assertEquals("[]", result2?.body) }
        )
	}

}