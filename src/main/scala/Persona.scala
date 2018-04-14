sealed trait Persona {
  val id: Id
  val nombre: Nombre
}

case class Natural(id: Id, nombre: Nombre) extends Persona
case class Jurídica(id: Id, nombre: Nombre) extends Persona

case class Id (valor: String)

object Id {
  def apply(valor: String): Id = new Id(valor)
}