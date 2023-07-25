package com.siriusxm.example.cart.pricing.github

import cats.effect.{IO, Ref}
import cats.implicits.*
import com.siriusxm.example.cart.model.{Cart, CartItem, Product}
import munit.CatsEffectSuite
import sttp.client3.Response
import sttp.client3.testing.SttpBackendStub
import sttp.monad.MonadAsyncError
import cats.effect._
import sttp.client3.impl.cats.implicits._
import sttp.monad.MonadAsyncError

class FetchPricesFromGithubSuite extends CatsEffectSuite {
  private val testingBackend = SttpBackendStub(implicitly[MonadAsyncError[IO]])
    .whenRequestMatches(_.uri.path.endsWith(List("cheerios.json")))
    .thenRespondF { _ =>
      IO(Response.ok(
        """{
      |  "title": "Cheerios",
      |  "price": 8.43
      |}""".stripMargin
      ))
    }
  private val subject        = new FetchPricesFromGithub(testingBackend)

  test("gets the correct price") {
    for {
      price <- subject.fetchProductPrice("cheerios")
    } yield assertEquals(price, BigDecimal(8.43))
  }
}
