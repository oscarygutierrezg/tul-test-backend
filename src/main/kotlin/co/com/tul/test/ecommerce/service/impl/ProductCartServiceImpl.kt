package co.com.tul.test.ecommerce.persistence.service

import org.springframework.stereotype.Service
import co.com.tul.test.ecommerce.persistence.repository.ProductCartRepository
import co.com.tul.test.ecommerce.persistence.model.ProductCart
import co.com.tul.test.ecommerce.exception.ProductCartNotFoundException
import org.springframework.http.HttpStatus
import java.util.UUID
import co.com.tul.test.ecommerce.persistence.model.pk.ProductCartPK
import co.com.tul.test.ecommerce.persistence.model.Product
import co.com.tul.test.ecommerce.persistence.model.Cart

@Service
class ProductCartServiceImpl(private val productCartRepository: ProductCartRepository) : ProductCartService  {
	
	 companion object {
        val NOT_FOUND_DESC = "No matching product cart was found"
    }
	
	override fun getAllProductsCartByProduct(product: Product): List<ProductCart> = productCartRepository.getAllProductCartsByProduct(product)

	override  fun getAllProductsCartByCart(cart : Cart): List<ProductCart> = productCartRepository.getAllProductCartsByCart(cart)

    override fun getProductCartById(productCartId: ProductCartPK): ProductCart= productCartRepository.findById(productCartId)
            .orElseThrow { ProductCartNotFoundException(HttpStatus.NOT_FOUND, NOT_FOUND_DESC) }
	
    override  fun createProductCart(productCart: ProductCart): ProductCart = productCartRepository.save(productCart)

   override  fun deleteProductCartById(productCartId: ProductCartPK) {
        return if (productCartRepository.existsById(productCartId)) {
            productCartRepository.deleteById(productCartId)
        } else throw ProductCartNotFoundException(HttpStatus.NOT_FOUND, NOT_FOUND_DESC)
    }

    override fun updateProductCart(productCart: ProductCart): ProductCart {
        return if (productCartRepository.existsById(productCart.id!!)) {
            productCartRepository.save(
                  productCart
            )
        } else throw ProductCartNotFoundException(HttpStatus.NOT_FOUND,NOT_FOUND_DESC )
    }


}