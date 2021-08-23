package co.com.tul.test.ecommerce.persistence.service

import org.springframework.stereotype.Service
import co.com.tul.test.ecommerce.persistence.repository.ProductRepository
import co.com.tul.test.ecommerce.persistence.model.Product
import co.com.tul.test.ecommerce.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import java.util.UUID

interface ProductService {

    fun getAllProducts(): List<Product> 

    fun getProductById(productId: UUID): Product
	
    fun createProduct(product: Product): Product

    fun updateProductById(productId: UUID, product: Product): Product 

    fun deleteProductsById(productId: UUID) 
}