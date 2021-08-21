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


/**
 * Controller for REST API endpoints
 */
@RestController
class ProductController(private val productService: ProductService) {

    @GetMapping("/product")
    fun getAllProducts(): List<Product> = productService.getAllProducts()

    @GetMapping("/product/{id}")
    fun getProductsById(@PathVariable("id") employeeId: UUID):EntityModel<Product>  {
            var p = productService.getProductsById(employeeId)
		var resource = EntityModel.of(p);

		var linkToAll = linkTo(WebMvcLinkBuilder.methodOn(ProductController::class.java).getAllProducts());
		
		var linkToOne = linkTo(WebMvcLinkBuilder.methodOn(ProductController::class.java).getProductsById(p.id!!));

		resource.add(linkToAll.withRel("all-products"));
		resource.add(linkToOne.withRel("self"));
		return resource;

	}
            

    @PostMapping("/product")
    fun createProduct(@Valid @RequestBody payload: Product): ResponseEntity<Map<String,URI>> {
		println("AQUIIII")
    	var product = productService.createProduct(payload)
    	var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(product.id).toUri()
		val map = mapOf("href" to location)
    	return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PutMapping("/product/{id}")
    fun updateProductById(@PathVariable("id") employeeId: UUID, @RequestBody payload: Product): Product =
            productService.updateProductById(employeeId, payload)

    @DeleteMapping("/product/{id}")
    fun deleteProductsById(@PathVariable("id") employeeId: UUID): Unit =
            productService.deleteProductsById(employeeId)
}