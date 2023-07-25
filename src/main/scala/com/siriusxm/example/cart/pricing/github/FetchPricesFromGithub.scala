package com.siriusxm.example.cart.pricing.github

import cats.effect.IO
import cats.implicits.*
import com.siriusxm.example.cart.pricing.FetchPrices
import sttp.capabilities.WebSockets
import sttp.client3.*
import sttp.client3.circe.*
import io.circe.generic.auto.*

import scala.math.BigDecimal.RoundingMode

private[github] final case class ProductPricePayload(title: String, price: BigDecimal)

class FetchPricesFromGithub(backend: SttpBackend[IO, WebSockets]) extends FetchPrices {
  private val host = "https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main"

  override def fetchProductPrice(productName: String): IO[BigDecimal] = for {
    r            <- basicRequest.get(uri"$host/${productName.toLowerCase}.json").response(asJsonAlways[ProductPricePayload]).send(
                      backend
                    )
    productPrice <- IO.fromEither(r.body)
  } yield productPrice.price.setScale(2, RoundingMode.UP)
}
