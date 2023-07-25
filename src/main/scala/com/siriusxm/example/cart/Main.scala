package com.siriusxm.example.cart

import cats.effect.IOApp
import cats.effect.IO
import cats.effect.IO
import cats.effect.kernel.Ref
import com.siriusxm.example.cart.model.Cart
import com.siriusxm.example.cart.pricing.FetchPrices
import com.siriusxm.example.cart.pricing.github.FetchPricesFromGithub
import sttp.client3.*
import sttp.client3.httpclient.cats.HttpClientCatsBackend

object Main extends IOApp.Simple {

  /** An example from README that should probably be an e2e test
    */
  private def example(addToCart: AddToCart): IO[Unit] = for {
    cartRef <- Ref[IO].of(Cart.empty)
    // Add 2 × cornflakes @ 2.52 each
    _       <- addToCart.add(cartRef, "cornflakes", 2)
    // Add 1 × weetabix @ 9.98 each
    _       <- addToCart.add(cartRef, "weetabix", 1)
    cart    <- cartRef.get
    subtotal = CalculateTotals.subtotal(cart)
    tax      = CalculateTotals.taxPayable(cart)
    total    = CalculateTotals.totalPayable(cart)
    _       <- IO.println(
                 s"""
         | Subtotal = $subtotal
         | Tax = $tax
         | Total = $total
         |""".stripMargin
               )
  } yield ()

  def run: IO[Unit] = HttpClientCatsBackend.resource[IO]().use { backend =>
    val fetchPrices = new FetchPricesFromGithub(backend)
    example(new AddToCart(fetchPrices))
  }
}
