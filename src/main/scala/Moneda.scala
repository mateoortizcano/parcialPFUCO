sealed trait Moneda {val valor: Double}

case class USD(valor: Double = 1d) extends Moneda
case class COP(valor: Double = 3000d) extends Moneda
case class EUR(valor: Double = 1.003d) extends Moneda

object tasaCambio {
  def
}
