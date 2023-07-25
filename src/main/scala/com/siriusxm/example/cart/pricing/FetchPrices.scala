package com.siriusxm.example.cart.pricing

import cats.effect.IO

trait FetchPrices {
  def fetchProductPrice(productName: String): IO[BigDecimal]
}
