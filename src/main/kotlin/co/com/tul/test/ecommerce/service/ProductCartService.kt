package co.com.tul.test.ecommerce.persistence.service

import org.springframework.stereotype.Service
import co.com.tul.test.ecommerce.persistence.repository.CartRepository
import co.com.tul.test.ecommerce.persistence.model.Cart
import org.springframework.http.HttpStatus
import java.util.UUID
import co.com.tul.test.ecommerce.persistence.model.ProductCart
import co.com.tul.test.ecommerce.persistence.model.pk.ProductCartPK
import co.com.tul.test.ecommerce.persistence.model.Product

interface ProductCartService {

    fun getAllProductsCartByProduct(product: Product): List<ProductCart>

	fun getAllProductsCartByCart(cart: Cart): List<ProductCart>

    fun getProductCartById(productCartId: ProductCartPK): ProductCart
	
    fun createProductCart(productCart: ProductCart): ProductCart

    fun updateProductCart(productCart: ProductCart): ProductCart 

    fun deleteProductCartById(productCartId: ProductCartPK) 
}