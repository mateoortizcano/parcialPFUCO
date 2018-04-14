case class Saldo (cantidad: Double, moneda: Moneda)

object  Saldo {
  def apply(cantidad: Double, moneda: Moneda): Saldo = new Saldo(cantidad, moneda)
}
