import scala.util.Try

trait Operacion {
  def consignar(saldo: Saldo, cuenta: Cuenta): Try[String]
  def retirar(saldo: Saldo, cuenta: Cuenta): Try[String]
  def transferir (saldo: Saldo, origen: Cuenta, destino: Cuenta): Try[String]
  def mostrarSaldo(cuenta: Cuenta): Try[String]
  def cancelarCuenta (cuenta: Cuenta): Try[String]
}


