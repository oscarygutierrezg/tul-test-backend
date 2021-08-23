package co.com.tul.test.ecommerce.persistence.service

import org.springframework.stereotype.Service
import co.com.tul.test.ecommerce.persistence.repository.CartRepository
import co.com.tul.test.ecommerce.persistence.model.Cart
import org.springframework.http.HttpStatus
import java.util.UUID

interface CartService {

    fun getAllCarts(): List<Cart> 

    fun getCartById(cartId: UUID): Cart
	
    fun createCart(cart: Cart): Cart

    fun updateCartById(cartId: UUID, cart: Cart): Cart 

    fun deleteCartsById(cartId: UUID) 
}