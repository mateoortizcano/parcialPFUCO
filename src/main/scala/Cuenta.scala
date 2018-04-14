import java.util.Date

import scala.util.{Failure, Success, Try}

sealed trait Cuenta {
  val nombre: Nombre
  val numero: Numero
  val propietario: Persona
  val saldo: Saldo
  val fechaApertura: Fecha
  val estado: Estado
  val fechaCierre: Fecha
}

case class Cheques (nombre: Nombre, numero:Numero, propietario: Persona,
                    saldo: Saldo, fechaApertura: Fecha, estado:  Estado,
                    fechaCierre: Fecha = Fecha(Date))  extends Cuenta {
}

case class Fiduciaria (nombre: Nombre, numero:Numero, propietario: Persona,
                       saldo: Saldo, fechaApertura: Fecha, estado:  Estado,
                       fechaCierre: Fecha = Fecha(Date))  extends Cuenta

case class Ahorros (nombre: Nombre, numero:Numero, propietario: Persona,
                    saldo: Saldo, fechaApertura: Fecha, estado:  Estado,
                    fechaCierre : Fecha = Fecha(Date))  extends Cuenta

object Ahorros extends Operacion {
  def apply(nombre: Nombre, numero: Numero, propietario: Persona,
            fechaApertura: Fecha): Ahorros = {
    val estado = Estado("1")
    val saldo = Saldo(0D,COP())
    new Ahorros(nombre, numero, propietario, saldo, fechaApertura, estado)
  }

  def generarConsignacion(saldo: Saldo, cuenta: Cuenta): Unit = {
    if (cuenta.saldo.moneda.getClass == saldo.moneda.getClass)
      cuenta.saldo.copy(cantidad = cuenta.saldo.cantidad + saldo.cantidad)
    else
      ???
  }

  override def consignar(saldo: Saldo, cuenta: Cuenta): Try[Unit] = {
    cuenta.estado.valor match {
      case "3" => Failure(CuentaCerradaException)
      case "1" | "2" => Success(generarConsignacion(saldo, cuenta))
      case _ => Failure(EstadoInvalidoException)
    }
  }

  override def retirar(saldo: Saldo, cuenta: Cuenta): Try[String] = ???

  override def transferir(saldo: Saldo, origen: Cuenta, destino: Cuenta): Try[String] = ???

  def calcularSaldo(cuenta: Cuenta): Saldo = cuenta.saldo

  override def mostrarSaldo(cuenta: Cuenta): Try[Saldo] = {
    cuenta.estado.valor match {
      case "1" => Failure(CuentaNoActivaException)
      case "3" => Failure(CuentaCerradaException)
      case "2" => Success(calcularSaldo(cuenta))
      case _ => Failure(EstadoInvalidoException)
    }
  }

  def cambiarEstado(cuenta: Cuenta): Estado = {
    cuenta.fechaCierre.copy(valor = new Date)
    cuenta.estado.copy(valor = "3")
    cuenta.estado
  }

  override def cancelarCuenta(cuenta: Cuenta): Try[Estado] = {
    cuenta.estado.valor match {
      case "1" => Failure(CuentaNoActivaException)
      case "3" => Failure(CuentaCerradaException)
      case "2" => Success(cambiarEstado(cuenta))
      case _ => Failure(EstadoInvalidoException)
    }
  }

}

case class Nombre (valor: String)

object  Nombre {
  def apply(valor: String): Nombre = new Nombre(valor)
}

case class Fecha (valor: Date)

object Fecha {
  def apply(valor: Date): Fecha = new Fecha(valor)
}

case class Numero (valor: Int)

object Numero {
  def apply(valor: Int): Numero = new Numero(valor)
}

case class Estado (valor: String)

object  Estado {
  //Estado 1: creada, 2: transacciones, 3: eliminada
  def apply(valor: String): Estado = new Estado(valor)
}