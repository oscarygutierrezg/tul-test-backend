package co.com.tul.test.ecommerce.persistence.service

import org.springframework.stereotype.Service
import co.com.tul.test.ecommerce.persistence.repository.CartRepository
import co.com.tul.test.ecommerce.persistence.model.Cart
import co.com.tul.test.ecommerce.exception.CartNotFoundException
import org.springframework.http.HttpStatus
import java.util.UUID

@Service
class CartServiceImpl(private val cartRepository: CartRepository) : CartService  {
	
	 companion object {
        val NOT_FOUND_DESC = "No matching cart was found"
    }

    override fun getAllCarts(): List<Cart> = cartRepository.findAll()

    override fun getCartById(cartId: UUID): Cart = cartRepository.findById(cartId)
            .orElseThrow { CartNotFoundException(HttpStatus.NOT_FOUND, NOT_FOUND_DESC) }

    override fun createCart(cart: Cart): Cart = cartRepository.save(cart)

    override fun updateCartById(cartId: UUID, cart: Cart): Cart {
        return if (cartRepository.existsById(cartId)) {
            cartRepository.save(
                    Cart(
						id = cartId,
						descuento= cart.descuento,
					    total= cart.total,
					    estado= cart.estado,
					    cartProduct= cart.cartProduct,
                    )
            )
        } else throw CartNotFoundException(HttpStatus.NOT_FOUND,NOT_FOUND_DESC )
    }

    override fun deleteCartsById(cartId: UUID) {
        return if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId)
        } else throw CartNotFoundException(HttpStatus.NOT_FOUND, NOT_FOUND_DESC)
    }
}