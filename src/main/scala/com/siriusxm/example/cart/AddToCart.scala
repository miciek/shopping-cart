package com.siriusxm.example.cart

import cats.effect.{IO, Ref}
import com.siriusxm.example.cart.model.{Cart, CartItem, Product}
import com.siriusxm.example.cart.pricing.FetchPrices

class AddToCart(fetchPrices: FetchPrices) {
  def add(cartRef: Ref[IO, Cart], productName: String, quantity: Int): IO[Cart] = {
    for {
      productPrice <- fetchPrices.fetchProductPrice(productName)
      product       = Product(productName, productPrice)
      updatedCart  <- cartRef.updateAndGet { cart =>
                        addNewCartItem(cart, product, quantity)
                      }
    } yield updatedCart
  }

  private def addNewCartItem(cart: Cart, product: Product, quantity: Int): Cart = {
    val quantityAlreadyInCart = cart.items.filter(_.product.name == product.name).map(_.quantity).sum
    val newQuantity           = quantityAlreadyInCart + quantity

    val otherCartItems = cart.items.filterNot(_.product.name == product.name)
    if (newQuantity > 0) {
      Cart(CartItem(product, newQuantity) :: otherCartItems)
    } else {
      Cart(otherCartItems)
    }
  }
}
