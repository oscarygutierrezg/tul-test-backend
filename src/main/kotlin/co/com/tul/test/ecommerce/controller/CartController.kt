package co.com.tul.test.ecommerce.controller


import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import co.com.tul.test.ecommerce.persistence.service.CartService
import co.com.tul.test.ecommerce.persistence.model.Cart
import java.util.UUID
import org.springframework.http.ResponseEntity
import java.net.URI
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.http.HttpStatus
import org.springframework.hateoas.EntityModel
import  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.links.Link
import org.springframework.http.MediaType
import javax.validation.Valid
import co.com.tul.test.ecommerce.exception.model.CustomErrorResponse
import io.swagger.v3.oas.annotations.Parameter
import co.com.tul.test.ecommerce.dto.model.LinkDTO
import co.com.tul.test.ecommerce.dto.model.CartDTO

@RestController
class CartController(private val cartService: CartService) {

	@Operation(summary = "Obtiene todos los carritos de compras")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Listado de carrito de comprass", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = ArraySchema(schema = Schema(implementation = Cart::class))
			)
		)])])
    @GetMapping("/cart")
    fun getAllCarts(): List<Cart> = cartService.getAllCarts()

	@Operation(summary = "Obtiene un carrito de compras dado el id")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Producto obtenido", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CartDTO::class)
			)
		)]),
	ApiResponse(responseCode = "404", description = "Producto no encontrado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])
	
	
	])
    @GetMapping("/cart/{id}")
    fun getCartsById(@Parameter(description = "Id del carrito de compras a buscar", example="ced4e507-6fff-46f2-b722-59e497f30f05")@PathVariable("id") cartId: UUID):EntityModel<Cart>  {
        val p = cartService.getCartById(cartId)
		val resource = EntityModel.of(p);

		val linkToAll = linkTo(WebMvcLinkBuilder.methodOn(CartController::class.java).getAllCarts());
		
		val linkToOne = linkTo(WebMvcLinkBuilder.methodOn(CartController::class.java).getCartsById(p.id!!));

		resource.add(linkToAll.withRel("all-carts"));
		resource.add(linkToOne.withRel("self"));
		return resource;

	}
     
	       
	@Operation(summary = "Crea un carrito de compras nuevo")
	@ApiResponses(value = [
	ApiResponse(responseCode = "201", description = "Nuevo recurso asociado al carrito de compras que fue creado", content = [
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
    @PostMapping("/cart")
    fun createCart(@Valid @RequestBody payload: Cart): ResponseEntity<Map<String,URI>> {
    	val cart = cartService.createCart(payload)
    	val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(cart.id).toUri()
		val map = mapOf("href" to location)
    	return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

	@Operation(summary = "Actuliza un carrito de compras existente")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Recurso asociado al carrito de compras que fue modificado", content = [
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
    @PutMapping("/cart/{id}")
    fun updateCartById(@PathVariable("id") cartId: UUID, @RequestBody payload: Cart): ResponseEntity<Map<String,URI>> {
    	cartService.updateCartById(cartId, payload)
    	val location = ServletUriComponentsBuilder.fromCurrentRequest()
    			.buildAndExpand().toUri()
		val map = mapOf("href" to location)
    	return ResponseEntity.status(HttpStatus.CREATED).body(map);
    } 
	
	
	@Operation(summary = "Elimina un carrito de compras existente")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Exitosa si el carrito de compras que fue emiminado", content = [
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
    @DeleteMapping("/cart/{id}")
    fun deleteCartsById(@PathVariable("id") cartId: UUID): Unit =
            cartService.deleteCartsById(cartId)
}