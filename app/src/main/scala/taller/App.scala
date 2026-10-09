package taller

object App {
  def main(args: Array[String]): Unit = {
    val c = new CifradosClasicos()
    //Pruebas basicas de las funciones
    //punto 1
    println(c.cesar("casa", 3))
    //punto 2
    println(c.cesarCola("casa", 3))
    //punto 3
    println(c.frecuencias("casabbbb"))
    //punto4
    println(c.desplazamientoProbable("h"))
    println(c.romperCesar(c.cesar("el mensaje secreto", 7)) )
    println(c.combinaciones(3, 26))
    //punto 5
    println(c.vigenere("ataque","sol"))
  }
}
