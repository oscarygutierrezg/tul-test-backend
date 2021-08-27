package co.com.tul.test.ecommerce.persistence.service

import org.springframework.stereotype.Service
import co.com.tul.test.ecommerce.persistence.repository.ProductRepository
import co.com.tul.test.ecommerce.persistence.model.Product
import co.com.tul.test.ecommerce.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import java.util.UUID

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService  {
	
	 companion object {
        val NOT_FOUND_DESC = "No matching product was found"
    }

    override fun getAllProducts(): List<Product> = productRepository.findAll()
    
	override fun getBySku(sku: String): List<Product> = productRepository.getBySku(sku)

    override fun getProductById(productId: UUID): Product = productRepository.findById(productId)
            .orElseThrow { ProductNotFoundException(HttpStatus.NOT_FOUND, NOT_FOUND_DESC) }

    override fun createProduct(product: Product): Product = productRepository.save(product)

    override fun updateProductById(productId: UUID, product: Product): Product {
        return if (productRepository.existsById(productId)) {
            productRepository.save(
                    Product(
						id=productId,
						nombre= product.nombre,
					    sku= product.sku,
					    descripcion= product.descripcion,
					    precio= product.precio,
						porcentajeDescuento= product.porcentajeDescuento,
					    tipoProducto= product.tipoProducto,
					    productCart= product.productCart,
                    )
            )
        } else throw ProductNotFoundException(HttpStatus.NOT_FOUND,NOT_FOUND_DESC )
    }

    override fun deleteProductsById(productId: UUID) {
        return if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId)
        } else throw ProductNotFoundException(HttpStatus.NOT_FOUND, NOT_FOUND_DESC)
    }
}