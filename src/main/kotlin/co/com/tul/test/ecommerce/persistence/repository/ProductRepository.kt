package co.com.tul.test.ecommerce.persistence.repository

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import co.com.tul.test.ecommerce.persistence.model.Product
import java.util.UUID

@Repository
interface ProductRepository : JpaRepository<Product, UUID>{
	
	fun getBySku(sku : String): List<Product>
}