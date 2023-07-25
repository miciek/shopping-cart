package com.siriusxm.example.cart

import com.siriusxm.example.cart.model.Cart

import java.math.MathContext
import scala.math.BigDecimal.RoundingMode

object CalculateTotals {
  def subtotal(cart: Cart): BigDecimal = cart.items.map(item => item.quantity * item.product.price).sum

  def taxPayable(cart: Cart): BigDecimal = (subtotal(cart) * 0.125).setScale(2, RoundingMode.UP)

  def totalPayable(cart: Cart): BigDecimal = subtotal(cart) + taxPayable(cart)
}
