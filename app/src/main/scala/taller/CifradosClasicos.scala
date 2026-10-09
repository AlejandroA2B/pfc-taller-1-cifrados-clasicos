package taller

import scala.annotation.tailrec

/**
 * Taller 1 — cifrados clásicos con recursión.
 *
 * Solo se cifran las 26 letras minúsculas del alfabeto inglés; cualquier otro
 * carácter se copia sin cambio.
 */
class CifradosClasicos {

  type Mensaje = String
  type Clave = String

  // Una frecuencia asocia cada letra con las veces que aparece.
  type Frecuencias = List[(Char, Int)]

  val letras = 26
  val primera = 'a'.toInt

  def esMinuscula(c: Char): Boolean = c >= 'a' && c <= 'z'

  // Punto 1 -------------------------------------------------------------------

  /** César con recursión lineal: una operación pendiente por letra. */
  def cesar(m: Mensaje, k: Int): Mensaje = {
    def moduloPositivo(n: Int, m: Int): Int = ((n % m) + m) % m//Siempre debe dar positivo
      def aux(word: Mensaje): String={

        if (word.isEmpty) {
        ""
        }else{
          if(esMinuscula(word.head.toChar)){ //Si es una letra en minuscula se puede cifrar
            val wordCifrado =(primera + moduloPositivo
            (word.head - primera + k, letras)).toChar

            wordCifrado+:aux(word.tail)
          }else{//No se cumple la condicion por lo tanto la letra u caracter no se cifra
            word.head+:aux(word.tail)
          }

      }
    }

    aux(m)

  }


  // Punto 2 -------------------------------------------------------------------

  /**
   * El mismo César como proceso iterativo: espacio constante.
   * Cuando la función esté escrita, anótela con @tailrec: el compilador
   * comprueba que la llamada recursiva sea lo último que hace.
   */

  final def cesarCola(m: Mensaje, k: Int, acc: Mensaje = ""): Mensaje = {
    def moduloPositivo(n: Int, m: Int): Int = ((n % m) + m) % m//Siempre debe dar positivo
    if(m.isEmpty){
      acc.reverse//Metodo reverse permite que una cadena ejemplo acb se organiza ala inversa quedando tal que bca
    }else{
      if(esMinuscula(m.head)){ //Si es una letra en minuscula se puede cifrar
        val wordCifrado =(primera + moduloPositivo
        (m.head - primera + k, letras)).toChar

        cesarCola(m.tail,k,wordCifrado+:acc)
      }else{//No se cumple la condicion por lo tanto la letra u caracter no se cifra
        cesarCola(m.tail,k, m.head+:acc)
      }
    }
  }

  // Punto 3 -------------------------------------------------------------------

  /**
   * Cuenta las letras minúsculas del mensaje, de mayor a menor frecuencia y,
   * en empate, en orden alfabético. El recorrido es recursivo de cola.
   */
  def frecuencias(m: Mensaje): Frecuencias = {

     def aux(word:Mensaje,acc:Map[Char, Int]):Map[Char, Int]= {
       if (word.isEmpty) {
        acc
       } else {
         if(esMinuscula(word.head)){
                                  //(key del map ,suma de esa key que se va acumulando)
             aux(word.tail, acc + (word.head ->
               (acc.getOrElse(word.head, 0) + 1)))
           //sumar cuantas veces se repita esa key en una sola recursion
         }else{
           aux(word.tail,acc)
         }
       }
    }
      aux(m,Map.empty).toList.sortBy(w => (-w._2,w._1))//Se pone -w._2 que significaria el numero negativo .El objetivo de ponerlo asi es que como ordena ascendente se le pone negativo para que lo ordene decendente
  }

  // Punto 4 -------------------------------------------------------------------

  /**
   * Supone que la letra más frecuente del mensaje cifrado es la 'e' del
   * original y devuelve la distancia entre las dos. Sin letras, cero.
   */
  def desplazamientoProbable(m: Mensaje): Int = {
    def moduloPositivo(n: Int, m: Int): Int = ((n % m) + m) % m
    val frecuenciasWords=frecuencias(m)
    if(frecuenciasWords.isEmpty){ //Si no se introduce nada devuelve cero
      0
    }else{
                     //letra mas frecuente - 'e' como referencias
      moduloPositivo(frecuenciasWords.head._1-'e',letras)
    }               //se accedio ala letra de la tupla con ._1
  }

  def romperCesar(m: Mensaje): Mensaje = {
    cesar(m,-desplazamientoProbable(m))
  }

  // Punto 5 -------------------------------------------------------------------

  /**
   * Cuántos mensajes de longitud n se forman con a letras sin dos iguales
   * seguidas.
   */
  def combinaciones(n: Int, a: Int): BigInt = {

    def aux(n_aux: Int, acc: BigInt=1): BigInt = {
      if (n_aux == 0)
        acc //Devuelvo acumulador
      else
        aux(n_aux - 1, acc * (a - 1))//Recursion de cola usando acumulador
    }

    if (n == 0) {
      BigInt(1)  //Devuelve 1 en un caso limite donde n sea 0
    } else{
      BigInt(a) * aux(n - 1, BigInt(1)) //recursion donde se va multiplicando y acumulando marcos de pila
    }

  }

  /**
   * Vigenère: cada letra se corre según la letra de la clave que le toca. Lo
   * que no es letra minúscula se copia y no consume clave.
   */
  def vigenere(m: Mensaje, clave: Clave): Mensaje = {

    if(clave.isEmpty){
      m
    }else {
      def moduloPositivo(n: Int, m: Int): Int = ((n % m) + m) % m

      def aux(word: String, idx: Int=0, acc: String=""): String = {
        if (word.isEmpty) {
          acc.reverse //Metodo reverse permite que una cadena ejemplo acb se organiza ala inversa quedando tal que bca
        } else {
          if (esMinuscula(word.head)) {
            val k = clave(idx % clave.length) - primera
            val wordCifrado = (primera + moduloPositivo(word.head - primera + k, letras)).toChar
            aux(word.tail, idx + 1, wordCifrado +: acc)
          } else {
            aux(word.tail, idx, word.head +: acc)
          }

        }

      }

      aux(m)//Invoco ala funcion auxiliar
    }
  }



}
