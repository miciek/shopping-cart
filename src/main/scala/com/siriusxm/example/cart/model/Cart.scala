package com.siriusxm.example.cart.model

final case class Cart(items: List[CartItem])

object Cart {
  val empty: Cart = Cart(List.empty)
}
