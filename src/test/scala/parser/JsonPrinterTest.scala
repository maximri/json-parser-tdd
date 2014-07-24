package printer

import domain._
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import parser.JsonPrinter

import scala.collection.immutable.Map

/**
 * Created by maximribakov on 7/23/14.
 */
class JsonPrinterTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val printer = JsonPrinter
  }

  "JsonPrinter" should {

    "print AST correctly for an empty string" in new Context {
      printer.printJsonObject(JsonObject()) === "{}"
    }

    "print AST correctly for one simple pair of domain.JsonNumber as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonNumber(3)))) === """{"key":3}"""
    }


    "print AST correctly for one simple pair of domain.JsonString as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonString("3")))) === """{"key":"3"}"""
    }

    "print AST correctly for one simple pair of JsonNull as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonNull))) === """{"key":null}"""
    }

    "print AST correctly for one simple pair of domain.JsonBoolean as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonBoolean(true)))) === """{"key":true}"""
    }

    "print AST correctly for one simple pair of empty Array as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray()))) === """{"key":[]}"""
    }

    "print AST correctly for one simple pair of an Array with domain.JsonNumber 3 value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonNumber(3))))) === """{"key":[3]}"""
    }

    "print AST correctly for one simple pair of an Array with domain.JsonNumber 4 value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonNumber(4))))) === """{"key":[4]}"""
    }

    "print AST correctly for one simple pair of an Array with domain.JsonString value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str"))))) === """{"key":["str"]}"""
    }

    "print AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonObject()))) === """{"key":{}}"""
    }

    "print AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonObject()))) === """{"key":{}}"""
    }

    "print AST correctly for two simple pairs of domain.JsonNumber as value" in new Context {
      printer.printJsonObject(JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonNumber(2)))) === """{"first":1,"second":2}"""
    }

    "print AST correctly for two simple pairs of domain.JsonNumber and domain.JsonString as value" in new Context {
      printer.printJsonObject(JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonString("2")))) === """{"first":1,"second":"2"}"""
    }


    /*Recursion test*/

    /*Array single level*/
    "print AST correctly for one simple pair of an Array with two JsonString's as values" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonString("str2"))))) === """{"key":["str1","str2"]}"""
    }

    "print AST correctly for one complex pair of an Array with two multipule values" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonNumber(4), JsonNull, JsonString("str2"))))) === """{"key":["str1",4,null,"str2"]}"""
    }

    /*Array double level*/


    "print AST correctly for one complex pair of an Array with an Inner Array as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2")))))) === """{"key":["str1",["str2"]]}"""
    }

    "print AST correctly for one complex pair of an Array with an Inner Array with as single value as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2")))))) === """{"key":["str1",["str2"]]}"""
    }

    "print AST correctly for one complex pair of an Array with an Inner Array with two values as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2"), JsonNumber(7)))))) === """{"key":["str1",["str2",7]]}"""
    }

    "print AST correctly for one complex pair of an Array with an Inner Array with two values which one of them is an object as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonArray(JsonString("str1"),
        JsonArray(JsonString("str2"),
          JsonObject(Map("keyInner" -> JsonString("val")))))))) === """{"key":["str1",["str2",{"keyInner":"val"}]]}"""

    }

    /*Object single level*/

    "print AST correctly for one simple pair of an Object with one JsonObject with one pair as value" in new Context {
      printer.printJsonObject(JsonObject(Map("key" -> JsonObject(Map("keyInner" -> JsonString("valueInner")))))) === """{"key":{"keyInner":"valueInner"}}"""
    }

    "print AST correctly for one simple pair of an Object with one JsonObject with two pairs as value" in new Context {
      printer.printJsonObject(
        JsonObject(Map("key" ->
          JsonObject(Map("keyInner" -> JsonString("valueInner"),
            "secondInner" -> JsonNull))))) === """{"key":{"keyInner":"valueInner","secondInner":null}}"""
    }

    /*Object double level*/
    "print AST correctly for one pair of an Object with an Inner Object as value that has another Inner Object" in new Context {
      printer.printJsonObject(JsonObject(Map("key" ->
        JsonObject(Map("keyInner" -> JsonString("valueInner"),
          "keyInnerObj" -> JsonObject(Map("secondInner" -> JsonNull))))))) === """{"key":{"keyInner":"valueInner","keyInnerObj":{"secondInner":null}}}"""

    }

    "print AST correctly for one pair of an Object with an Inner Object as value that has another Inner object with two elements" in new Context {
      printer.printJsonObject(JsonObject(Map("key" ->
        JsonObject(Map("keyInner" -> JsonString("valueInner"),
          "keyInnerObj" -> JsonObject(Map("secondInner" -> JsonNull, "secondInner1" -> JsonNumber(7)))))))) ===
        """{"key":{"keyInner":"valueInner","keyInnerObj":{"secondInner":null,"secondInner1":7}}}"""

    }

    "print AST correctly for one pair of an Object with an Inner Object as value that has another Inner Array with multipul elements" in new Context {
      printer.printJsonObject(JsonObject(Map("key" ->
        JsonObject(Map("keyInner" -> JsonString("valueInner"),
          "keyInnerObj" -> JsonArray(JsonString("secondInner"),
            JsonNull,
            JsonString("secondInner"),
            JsonNumber(7))))))) === """{"key":{"keyInner":"valueInner","keyInnerObj":["secondInner",null,"secondInner",7]}}"""

    }

    /* same key pairs */

    "print AST correctly for one simple pair of an Object with one JsonObject with two pairs as value that have the same key" in new Context {
      printer.printJsonObject(
        JsonObject(Map("key" ->
          JsonObject(Map("keyInner" -> JsonString("valueInner"),
            "keyInner2" -> JsonNull))))) === """{"key":{"keyInner":"valueInner","keyInner2":null}}"""
    }


  }

}
