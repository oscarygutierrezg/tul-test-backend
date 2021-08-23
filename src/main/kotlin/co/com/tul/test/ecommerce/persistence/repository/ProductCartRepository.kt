package co.com.tul.test.ecommerce.persistence.repository

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import co.com.tul.test.ecommerce.persistence.model.Cart
import java.util.UUID
import co.com.tul.test.ecommerce.persistence.model.ProductCart
import co.com.tul.test.ecommerce.persistence.model.pk.ProductCartPK
import co.com.tul.test.ecommerce.persistence.model.Product

@Repository
interface ProductCartRepository : JpaRepository<ProductCart, ProductCartPK>{
	
	fun getAllProductCartsByProduct(product: Product): List<ProductCart>

	fun getAllProductCartsByCart(cart : Cart): List<ProductCart>
}