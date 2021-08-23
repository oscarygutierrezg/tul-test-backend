package co.com.tul.test.ecommerce.controller


import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import co.com.tul.test.ecommerce.persistence.service.ProductCartService
import co.com.tul.test.ecommerce.persistence.model.ProductCart
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
import co.com.tul.test.ecommerce.persistence.service.CartService
import co.com.tul.test.ecommerce.persistence.service.ProductService
import co.com.tul.test.ecommerce.persistence.model.pk.ProductCartPK
import co.com.tul.test.ecommerce.dto.model.ProductCartDTO
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
import co.com.tul.test.ecommerce.dto.model.LinkProductCartDTO
import co.com.tul.test.ecommerce.persistence.model.ProductCartResDTO

@RestController
class ProductCartController (private val productCartService: ProductCartService,
		private val productService: ProductService,
		private val cartService: CartService) {

	@Operation(summary = "Obtiene todos los productos dado el id del producto")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Listado de productos", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = ArraySchema(schema = Schema(implementation = ProductCart::class))
			)
		)])])
	@GetMapping("/productCartByProduct/{productId}")
	fun productsCartByProduct(@PathVariable("productId") productId: UUID): List<ProductCart> {
		val p = productService.getProductById(productId)
		return productCartService.getAllProductsCartByProduct(p)
	}

	@Operation(summary = "Obtiene todos los productos dado el id del carrito de compras")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Listado de productos", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = ArraySchema(schema = Schema(implementation = ProductCart::class))
			)
		)])])
	@GetMapping("/productCartByCart/{cartId}")
	fun productsCartByCart(@PathVariable("cartId") cartId: UUID): List<ProductCart> {
		val c = cartService.getCartById(cartId)
		return productCartService.getAllProductsCartByCart(c)
	}

	@Operation(summary = "Obtiene un producto dado el id del carrito de compras y el id del producto")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Listado de productos", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = ArraySchema(schema = Schema(implementation = ProductCartResDTO::class))
			)
		)])])
	@GetMapping("/productCart/{productId}/{cartId}")
	fun getProductCartsById(@PathVariable("productId") productId: UUID, @PathVariable("cartId") cartId: UUID):EntityModel<ProductCart>  {
		val pk = ProductCartPK(cartId,productId)
		val pc = productCartService.getProductCartById(pk)
				val resource = EntityModel.of(pc);

		val linkToAllByProduct = linkTo(WebMvcLinkBuilder.methodOn(ProductCartController::class.java).productsCartByProduct(productId));
		val linkToAllByCart = linkTo(WebMvcLinkBuilder.methodOn(ProductCartController::class.java).productsCartByCart(cartId));

		val linkToOne = linkTo(WebMvcLinkBuilder.methodOn(ProductCartController::class.java).getProductCartsById(productId,cartId));

		resource.add(linkToAllByProduct.withRel("all-productscart-by-product"));
		resource.add(linkToAllByCart.withRel("all-productscart-by-cart"));
		resource.add(linkToOne.withRel("self"));
		return resource;

	}

	@Operation(summary = "Crea agrega un producto al carrito de compras")
	@ApiResponses(value = [
	ApiResponse(responseCode = "201", description = "Nuevo recurso asociado al carrito de compras que fue creado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = LinkProductCartDTO::class)
			)
		)]),
	ApiResponse(responseCode = "400", description = "Error", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])]
	 )
	@PostMapping("/productCart")
	fun createProductCart(@Valid @RequestBody payload: ProductCartDTO): ResponseEntity<Map<String,URI>> {
		val p = productService.getProductById(payload.productId!!)
		val c = cartService.getCartById(payload.cartId!!)
		val pk = ProductCartPK(payload.cartId,payload.productId)
		val productCart = productCartService.createProductCart(ProductCart(
			id=pk,
						 cart= c,
					    product= p,
					    cantidad= payload.cantidad,
                    ))
		val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}/{cartId}")
		.buildAndExpand(productCart.id!!.productid,productCart.id.cardid).toUri()
		val map = mapOf("href" to location)
		return ResponseEntity.status(HttpStatus.CREATED).body(map);
	}
	
	@Operation(summary = "Actualiza un producto del carrito de compras")
	@ApiResponses(value = [
	ApiResponse(responseCode = "201", description = "Nuevo recurso asociado al carrito de compras que fue creado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = LinkProductCartDTO::class)
			)
		)]),
	ApiResponse(responseCode = "400", description = "Error", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])]
	 )
	@PutMapping("/productCart")
	fun updateProductCart(@Valid @RequestBody payload: ProductCartDTO): ResponseEntity<Map<String,URI>> {
		val p = productService.getProductById(payload.productId!!)
		val c = cartService.getCartById(payload.cartId!!)
		val pk = ProductCartPK(payload.cartId,payload.productId)
		 productCartService.updateProductCart(
		ProductCart(
			id=pk,
						cart= c,
					    product= p,
					    cantidad= payload.cantidad,
                    ))
		val location = ServletUriComponentsBuilder.fromCurrentRequest().
		buildAndExpand().toUri()
		val map = mapOf("href" to location)
		return ResponseEntity.status(HttpStatus.CREATED).body(map);
	}

	@Operation(summary = "Elimina un producto del carrito de compras existente")
	@ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Exitosa si el producto que fue emiminado", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE
			)
		)]),
	ApiResponse(responseCode = "404", description = "Producto o carrito de compras no encontrados", content = [
	    (
			Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = Schema(implementation = CustomErrorResponse::class)
			)
		)])]
	 )
	@DeleteMapping("/productCart/{productId}/{cartId}")
	fun deleteProductCartById(@PathVariable("productId") productId: UUID, @PathVariable("cartId") cartId: UUID) {
		productCartService.deleteProductCartById(ProductCartPK(cartId,productId))
	}
}