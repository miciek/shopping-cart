package com.siriusxm.example.cart

import com.siriusxm.example.cart.model.{Cart, CartItem, Product}
import com.siriusxm.example.cart.pricing.FetchTestPrices
import munit.CatsEffectSuite

class AddToCartSuite extends CatsEffectSuite {
  private val subject = new AddToCart(new FetchTestPrices(Map("toothpaste" -> 1.5)))

  test("add a single product to an empty cart") {
    for {
      newCart <- subject.addToCart(Cart.empty, "toothpaste", 7)
    } yield assertEquals(newCart.items, List(CartItem(Product(name = "toothpaste", price = 1.5), quantity = 7)))
  }

  test("fail if product price cannot be fetched") {
    subject.addToCart(Cart.empty, "non existing", 7).intercept[Exception]
  }
}
