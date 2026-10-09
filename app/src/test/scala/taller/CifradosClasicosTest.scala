package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

/**
 * Cada ejemplo del enunciado es una prueba. Si el enunciado promete un valor,
 * aquí se comprueba que la solución lo produce.
 */
@RunWith(classOf[JUnitRunner])
class CifradosClasicosTest extends AnyFunSuite {

  val c = new CifradosClasicos()
  import c._

  // Punto 1: ejemplos del enunciado -------------------------------------------

  test("cesar: casa con 3 da fdvd") { assert(cesar("casa", 3) == "fdvd") }
  test("cesar: fdvd con -3 vuelve a casa") { assert(cesar("fdvd", -3) == "casa") }
  test("cesar: hola mundo con 1") { assert(cesar("hola mundo", 1) == "ipmb nvoep") }
  test("cesar: zzz con 1 da aaa") { assert(cesar("zzz", 1) == "aaa") }
  test("cesar: 29 es lo mismo que 3") { assert(cesar("abc", 29) == "def") }
  test("cesar: el mensaje vacío sale vacío") { assert(cesar("", 5) == "") }

  test("cesar: la puntuación y los dígitos pasan sin cambio") {
    assert(cesar("ab, 12!", 1) == "bc, 12!")
  }

  test("cesar: las mayúsculas no se cifran") {
    assert(cesar("Casa", 3) == "Cdvd")
  }

  test("cesar: cifrar y descifrar es la identidad") {
    assert(cesar(cesar("un mensaje cualquiera", 11), -11) == "un mensaje cualquiera")
  }

  //Test punto 1

  test("cesar: una sola letra con k grande da la vuelta completa sin fallar") {
    assert(cesar("a", 26) == "a")
    assert(cesar("a", 52) == "a")
  }

  test("cesar: k negativo grande equivale a su módulo respectivamente") {
    assert(cesar("a", -1) == "z")
    assert(cesar("abc", -29) == "xyz")
  }

  test("cesar: todos los caracteres que no son letras minusculas pasan intactos sin problema") {
    assert(cesar("¡Hola, Mundo! 123.", 5) == "¡Htqf, Mzsit! 123.")
  }

  test("cesar: el resultado tiene la misma longitud que la entrada") {
    val entrada = "hola mundo, 2026!"
    assert(cesar(entrada, 7).length == entrada.length)
  }

  test("cesar: cifrar con 0 no modifica el mensaje") {
    assert(cesar("cualquier cosa 123", 0) == "cualquier cosa 123")
  }

  // Punto 2 -------------------------------------------------------------------

  test("cesarCola: casa con 3 da fdvd") { assert(cesarCola("casa", 3) == "fdvd") }
  test("cesarCola: hola mundo con 1") { assert(cesarCola("hola mundo", 1) == "ipmb nvoep") }
  test("cesarCola: con 0 el mensaje no cambia") { assert(cesarCola("abc", 0) == "abc") }

  test("cesarCola: da lo mismo que la versión lineal") {
    val casos = List(("casa", 3), ("hola mundo", 1), ("zzz", 1), ("abc", 29),
                     ("", 5), ("ab, 12!", -4))
    assert(casos.forall { case (m, k) => cesarCola(m, k) == cesar(m, k) })
  }

  test("cesarCola: aguanta un mensaje largo sin desbordar la pila") {
    val largo = "abcdefghij" * 20000
    assert(cesarCola(largo, 1).length == largo.length)
  }

  //Test punto 2

  test("cesarCola: coincide con cesar para k negativo grande") {
    assert(cesarCola("abcdef", -100) == cesar("abcdef", -100))
  }

  test("cesarCola: cadena de solo símbolos no cambia") {
    assert(cesarCola("!@#$%^&*()", 13) == "!@#$%^&*()")
  }

  test("cesarCola: cadena de solo mayúsculas no cambia") {
    assert(cesarCola("HOLA MUNDO", 5) == "HOLA MUNDO")
  }

  test("cesarCola: mantiene la longitud en un mensaje mixto") {
    val entrada = "Aa1 Bb2 Cc3 ñÑ"
    assert(cesarCola(entrada, 3).length == entrada.length)
  }

  test("cesarCola: es idempotente al cifrar y descifrar con k grande") {
    val original = "texto de prueba con espacios"
    assert(cesarCola(cesarCola(original, 40), -40) == original)
  }

  // Punto 3 -------------------------------------------------------------------

  test("frecuencias: casa") {
    assert(frecuencias("casa") == List(('a', 2), ('c', 1), ('s', 1)))
  }

  test("frecuencias: aabbbc") {
    assert(frecuencias("aabbbc") == List(('b', 3), ('a', 2), ('c', 1)))
  }

  test("frecuencias: hola mundo") {
    assert(frecuencias("hola mundo") ==
      List(('o', 2), ('a', 1), ('d', 1), ('h', 1), ('l', 1), ('m', 1),
           ('n', 1), ('u', 1)))
  }

  test("frecuencias: el mensaje vacío no tiene letras") {
    assert(frecuencias("") == List())
  }

  test("frecuencias: un mensaje sin letras no tiene frecuencias") {
    assert(frecuencias("123 !?") == List())
  }

  test("frecuencias: en empate manda el orden alfabético") {
    assert(frecuencias("ba") == List(('a', 1), ('b', 1)))
  }

  //Tests 3
  test("frecuencias: una sola letra repetida") {
    assert(frecuencias("aaaaa") == List(('a', 5)))
  }

  test("frecuencias: todas las letras distintas quedan alfabéticas") {
    assert(frecuencias("zyx") == List(('x', 1), ('y', 1), ('z', 1)))
  }

  test("frecuencias: mayúsculas y minúsculas no se mezclan") {
    assert(frecuencias("AaA") == List(('a', 1)))
  }

  test("frecuencias: empate múltiple se ordena alfabéticamente") {
    assert(frecuencias("cba") == List(('a', 1), ('b', 1), ('c', 1)))
  }

  test("frecuencias: ignora puntuación, dígitos y espacios") {
    assert(frecuencias("a!b?c 1 2 3") == List(('a', 1), ('b', 1), ('c', 1)))
  }

  // Punto 4 -------------------------------------------------------------------

  test("desplazamientoProbable: h está 3 después de e") {
    assert(desplazamientoProbable("h") == 3)
  }

  test("desplazamientoProbable: hhhaa, con h como la más frecuente") {
    assert(desplazamientoProbable("hhhaa") == 3)
  }

  test("desplazamientoProbable: sin letras da 0") {
    assert(desplazamientoProbable("123") == 0)
  }

  test("desplazamientoProbable: en empate manda la primera alfabéticamente") {
    // 'a' y 'h' aparecen tres veces; gana 'a', que está 22 después de 'e'.
    assert(desplazamientoProbable("hhhaaa") == 22)
  }

  test("romperCesar: recupera un mensaje con suficientes letras e") {
    val original = "el mensaje secreto"
    assert(romperCesar(cesar(original, 7)) == original)
  }

  test("romperCesar: el método falla cuando la e no es la más frecuente") {
    // En este mensaje la letra más frecuente es la 'a', no la 'e'.
    val original = "cada casa amarilla"
    assert(romperCesar(cesar(original, 7)) != original)
  }

  //test 4 desplazamiento probable
  test("desplazamientoProbable: la 'e' más frecuente da 0") {
    assert(desplazamientoProbable("eeeffgg") == 0)
  }

  test("desplazamientoProbable: la 'f' más frecuente da 1") {
    assert(desplazamientoProbable("fffggg") == 1)
  }

  test("desplazamientoProbable: la 'a' más frecuente da 22") {
    assert(desplazamientoProbable("aaaaabbb") == 22)
  }

  test("desplazamientoProbable: la 'z' más frecuente da 21") {
    assert(desplazamientoProbable("zzzzzyyy") == 21)
  }

  test("desplazamientoProbable: sin letras devuelve 0") {
    assert(desplazamientoProbable("123 !?") == 0)
  }

   //test 4 romper cesar
  test("romperCesar: recupera un texto largo con k=1") {
    val original = "el elefante verde come entre el verde este y el oeste este verano"
    assert(romperCesar(cesar(original, 1)) == original)
  }

  test("romperCesar: recupera un texto largo con k=13") {
    val original = "este es el mejor texto secreto que se puede esconder entre estas letras"
    assert(romperCesar(cesar(original, 13)) == original)
  }

  test("romperCesar: mensaje vacío devuelve vacío") {
    assert(romperCesar("") == "")
  }

  test("romperCesar: mensaje sin letras devuelve el mismo mensaje") {
    assert(romperCesar("123 !?") == "123 !?")
  }

  test("romperCesar: falla cuando la letra dominante no es la 'e'") {
    val original = "zzzz zzzz zzzz"
    val cifrado = cesar(original, 3)
    assert(romperCesar(cifrado) != original)
  }


  // Punto 5 -------------------------------------------------------------------

  test("combinaciones: con longitud 0 hay un mensaje, el vacío") {
    assert(combinaciones(0, 26) == BigInt(1))
  }

  test("combinaciones: con longitud 1 hay tantos como letras") {
    assert(combinaciones(1, 26) == BigInt(26))
  }

  test("combinaciones: 3 letras sobre 26 dan 16250") {
    assert(combinaciones(3, 26) == BigInt(16250))
  }

  test("combinaciones: 2 letras sobre un alfabeto de 2 dan 2") {
    assert(combinaciones(2, 2) == BigInt(2))
  }

  test("combinaciones: crece según la recurrencia") {
    assert(combinaciones(5, 4) == BigInt(3) * combinaciones(4, 4))
  }

  test("vigenere: ataque con la clave sol") {
    assert(vigenere("ataque", "sol") == "shliip")
  }

  test("vigenere: hola mundo con la clave ab") {
    assert(vigenere("hola mundo", "ab") == "hplb mvneo")
  }

  test("vigenere: con la clave vacía el mensaje no cambia") {
    assert(vigenere("casa", "") == "casa")
  }

  test("vigenere: el espacio no consume letra de la clave") {
    // Sin el espacio la clave iría corrida y la m se cifraría con b.
    assert(vigenere("hola mundo", "ab").charAt(5) == 'm')
  }

  test("vigenere: con una clave de una sola letra es un César") {
    assert(vigenere("hola mundo", "d") == cesar("hola mundo", 3))
  }

  //Tests 5 combinaciones

  test("combinaciones: n=0 devuelve 1 para cualquier alfabeto") {
    assert(combinaciones(0, 26) == BigInt(1))
    assert(combinaciones(0, 5) == BigInt(1))
  }

  test("combinaciones: n=1 devuelve el tamaño del alfabeto") {
    assert(combinaciones(1, 26) == BigInt(26))
    assert(combinaciones(1, 7) == BigInt(7))
  }

  test("combinaciones: n=2, a=26 da 650") {
    assert(combinaciones(2, 26) == BigInt(650))   // 26 * 25
  }

  test("combinaciones: cumple la recurrencia del taller") {
    assert(combinaciones(4, 5) == BigInt(4) * combinaciones(3, 5))
    assert(combinaciones(5, 10) == BigInt(9) * combinaciones(4, 10))
  }

  test("combinaciones: alfabeto de 1 letra con n>1 da 0") {
    assert(combinaciones(2, 1) == BigInt(0))
    assert(combinaciones(5, 1) == BigInt(0))
  }

  //Tests 5 vigenere
  test("vigenere: 'abc' con 'xyz'") {
    assert(vigenere("abc", "xyz") == "xzb")
  }

  test("vigenere: clave de una sola letra se comporta como César") {
    assert(vigenere("hola mundo", "d") == cesar("hola mundo", 3))
  }

  test("vigenere: mensaje vacío devuelve vacío") {
    assert(vigenere("", "cualquierclave") == "")
  }

  test("vigenere: los dígitos no consumen letra de la clave") {
    assert(vigenere("a1a", "ab") == "a1b")
  }

  test("vigenere: las mayúsculas no se cifran ni consumen clave") {
    assert(vigenere("aAa", "ab") == "aAb")
  }
}
