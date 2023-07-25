package com.siriusxm.example.cart.pricing

import cats.effect.IO

/** Makes tests that use [[FetchPrices]] cleaner by converting a Map into an IO action.
  *
  * See [[com.siriusxm.example.cart.AddToCartSuite]] as an example of how to use it in tests.
  */
class FetchTestPrices(nameToPrice: Map[String, BigDecimal]) extends FetchPrices {
  override def fetchProductPrice(productName: String): IO[BigDecimal] = nameToPrice.get(productName).map(
    IO.pure
  ).getOrElse(IO.raiseError(new Exception(s"TEST ERROR: product $productName doesn't have a test price")))
}
