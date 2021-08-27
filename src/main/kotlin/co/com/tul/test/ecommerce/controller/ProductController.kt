package co.com.tul.test.ecommerce.controller


import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import co.com.tul.test.ecommerce.persistence.service.ProductService
import co.com.tul.test.ecommerce.persistence.model.Product
import java.util.UUID
import org.springframework.http.ResponseEntity
import java.net.URI
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.http.HttpStatus
import org.springframework.hateoas.EntityModel
import  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import javax.validation.Valid
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.links.Link
import co.com.tul.test.ecommerce.dto.model.ProductDTO
import co.com.tul.test.ecommerce.dto.model.ProductsDTO
import co.com.tul.test.ecommerce.dto.model.LinkDTO
import co.com.tul.test.ecommerce.exception.model.CustomErrorResponse
import org.springframework.http.MediaType
import io.swagger.v3.oas.annotations.Parameter

@RestController
class ProductController(private val productService: ProductService) {

	@Operation(summary = "Obtiene todos los productos")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Listado de productos", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = ArraySchema(schema = Schema(implementation = ProductsDTO::class))
			)
		)])])
    @GetMapping("/product")
    fun getAllProducts() = productService.getAllProducts()
	
	
	
	@Operation(summary = "Obtiene todos los productos por sku")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Listado de productos", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = ArraySchema(schema = Schema(implementation = ProductsDTO::class))
			)
		)])])
    @GetMapping("/product/sku/{sku}")
    fun getBySku(@Parameter(description = "Id del carrito de compras a buscar", example="RREWWERWRRRWE")@PathVariable("sku") sku: String): List<Product> = productService.getBySku(sku)


	@Operation(summary = "Obtiene un producto dado el id")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Producto obtenido", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = ProductDTO::class)
			)
		)]),
	ApiResponse(responseCode = "404", description = "Producto no encontrado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])
	
	
	])
    @GetMapping("/product/{id}")
    fun getProductsById(@Parameter(description = "Id del producto a buscar", example="ced4e507-6fff-46f2-b722-59e497f30f05")@PathVariable("id") productId: UUID):EntityModel<Product>  {
            var p = productService.getProductById(productId)
		var resource = EntityModel.of(p);

		var linkToAll = linkTo(WebMvcLinkBuilder.methodOn(ProductController::class.java).getAllProducts());
		
		var linkToOne = linkTo(WebMvcLinkBuilder.methodOn(ProductController::class.java).getProductsById(p.id!!));

		resource.add(linkToAll.withRel("all-products"));
		resource.add(linkToOne.withRel("self"));
		return resource;

	}
           
	@Operation(summary = "Crea un producto nuevo")
	@ApiResponses(value = [
	ApiResponse(responseCode = "201", description = "Nuevo recurso asociado al producto que fue creado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = LinkDTO::class)
			)
		)]),
	ApiResponse(responseCode = "400", description = "Error", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])]
	 )
    @PostMapping("/product", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createProduct(@Valid @RequestBody payload: Product): ResponseEntity<Map<String,URI>> {
    	val product = productService.createProduct(payload)
    	val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(product.id).toUri()
		val map = mapOf("href" to location)
    	return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

	@Operation(summary = "Actuliza un producto existente")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Recurso asociado al producto que fue modificado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = LinkDTO::class)
			)
		)]),
	ApiResponse(responseCode = "400", description = "Error", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)]),
	ApiResponse(responseCode = "404", description = "Producto no encontrado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])]
	 )
    @PutMapping("/product/{id}")
    fun updateProductById(@Parameter(description = "Id del producto por actualizar", example="ced4e507-6fff-46f2-b722-59e497f30f05") @PathVariable("id") productId: UUID, @RequestBody payload: Product): ResponseEntity<Map<String,URI>>{
       productService.updateProductById(productId, payload)
       val location = ServletUriComponentsBuilder.fromCurrentRequest()
    			.buildAndExpand().toUri()
       val map = mapOf("href" to location)
       return ResponseEntity.status(HttpStatus.OK).body(map);
		
	}

	@Operation(summary = "Elimina un producto existente")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Exitosa si el producto que fue emiminado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE
			)
		)]),
	ApiResponse(responseCode = "404", description = "Producto no encontrado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])]
	 )
    @DeleteMapping("/product/{id}")
    fun deleteProductsById(@Parameter(description = "Id del producto por eliminar", example="ced4e507-6fff-46f2-b722-59e497f30f05") @PathVariable("id") productId: UUID): Unit =
            productService.deleteProductsById(productId)
}