package com.siriusxm.example.cart

import cats.effect.{IO, Ref}
import cats.implicits._
import com.siriusxm.example.cart.model.{Cart, CartItem, Product}
import com.siriusxm.example.cart.pricing.FetchTestPrices
import munit.CatsEffectSuite

class AddToCartSuite extends CatsEffectSuite {
  private val subject = new AddToCart(new FetchTestPrices(Map("toothpaste" -> 1.5, "shampoo" -> 4)))

  test("add a single product to an empty cart") {
    for {
      cartRef <- Ref[IO].of(Cart.empty)
      newCart <- subject.addToCart(cartRef, "toothpaste", 7)
    } yield assertEquals(newCart.items, List(CartItem(Product(name = "toothpaste", price = 1.5), quantity = 7)))
  }

  test("fail if product price cannot be fetched") {
    (for {
      cartRef <- Ref[IO].of(Cart.empty)
      _       <- subject.addToCart(cartRef, "non existing", 7)
    } yield ()).intercept[Exception]
  }

  test("add multiple products to an existing cart") {
    for {
      cartRef <- Ref[IO].of(Cart(List(CartItem(Product(name = "soap", price = 0.7), quantity = 2))))
      _       <- List.fill(100)(subject.addToCart(cartRef, "toothpaste", 7)).parSequence
      _       <- List.fill(10)(subject.addToCart(cartRef, "shampoo", 3)).parSequence
      newCart <- cartRef.get
    } yield assertEquals(
      newCart.items.toSet,
      Set(
        CartItem(Product(name = "soap", price = 0.7), quantity = 2),
        CartItem(Product(name = "toothpaste", price = 1.5), quantity = 700), // 100 * 7
        CartItem(Product(name = "shampoo", price = 4), quantity = 30)        // 10 * 3
      )
    )
  }
}
