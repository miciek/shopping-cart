package com.siriusxm.example.cart

import cats.effect.IO
import com.siriusxm.example.cart.model.{Cart, CartItem, Product}
import com.siriusxm.example.cart.pricing.FetchPrices

class AddToCart(fetchPrices: FetchPrices) {
  def addToCart(cart: Cart, productName: String, quantity: Int): IO[Cart] = {
    for {
      productPrice <- fetchPrices.fetchProductPrice(productName)
      product       = Product(productName, productPrice)
      newCartItem   = CartItem(product, quantity)
    } yield cart.copy(items = cart.items.appended(newCartItem))
  }
}
