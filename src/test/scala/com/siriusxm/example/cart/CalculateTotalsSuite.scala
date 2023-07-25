package com.siriusxm.example.cart

import cats.effect.{IO, Ref}
import cats.implicits.*
import com.siriusxm.example.cart.model.{Cart, CartItem, Product}
import com.siriusxm.example.cart.pricing.FetchTestPrices
import munit.{CatsEffectSuite, FunSuite}

class CalculateTotalsSuite extends FunSuite {

  val nonEmptyCart = Cart(items =
    List(
      CartItem(Product(name = "soap", price = 0.7), quantity = 2),
      CartItem(Product(name = "toothpaste", price = 1.5), quantity = 7),
      CartItem(Product(name = "shampoo", price = 4), quantity = 3)
    )
  )

  test("Cart subtotal (sum of price for all items)") {
    assertEquals(CalculateTotals.subtotal(nonEmptyCart), BigDecimal(23.90))
  }

  test("Tax payable, charged at 12.5% on the subtotal") {
    assertEquals(CalculateTotals.taxPayable(nonEmptyCart), BigDecimal(2.99)) // 2.9875
  }

  test("Total payable (subtotal + tax)") {
    assertEquals(CalculateTotals.totalPayable(nonEmptyCart), BigDecimal(26.89))
  }

  test("Cart subtotal (sum of price for all items) for an empty cart") {
    assertEquals(CalculateTotals.subtotal(Cart.empty), BigDecimal(0))
  }

  test("Tax payable, charged at 12.5% on the subtotal for an empty cart") {
    assertEquals(CalculateTotals.taxPayable(Cart.empty), BigDecimal(0))
  }

  test("Total payable (subtotal + tax) for an empty cart") {
    assertEquals(CalculateTotals.totalPayable(Cart.empty), BigDecimal(0))
  }
}
