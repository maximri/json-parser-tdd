package parser

import domain._
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by maximribakov on 7/22/14.
 */
class JsonParserTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val parser = JsonParser
  }

  "JsonParser" should {

    "construct AST correctly for an empty string" in new Context {
      parser.parseJsonObject("{}") === JsonObject()
    }

    "construct AST correctly for one simple pair of domain.JsonNumber as value" in new Context {
      parser.parseJsonObject( """{"key":3}""") === JsonObject(Map("key" -> JsonNumber(3)))
    }

    "construct AST correctly for one simple pair of domain.JsonString as value" in new Context {
      parser.parseJsonObject( """{"key":"3"}""") === JsonObject(Map("key" -> JsonString("3")))
    }

    "construct AST correctly for one simple pair of JsonNull as value" in new Context {
      parser.parseJsonObject( """{"key":null}""") === JsonObject(Map("key" -> JsonNull))
    }

    "construct AST correctly for one simple pair of domain.JsonBoolean as value" in new Context {
      parser.parseJsonObject( """{"key":true}""") === JsonObject(Map("key" -> JsonBoolean(true)))
    }

    "construct AST correctly for one simple pair of empty Array as value" in new Context {
      parser.parseJsonObject( """{"key":[]}""") === JsonObject(Map("key" -> JsonArray()))
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonNumber 3 value" in new Context {
      parser.parseJsonObject( """{"key":[3]}""") === JsonObject(Map("key" -> JsonArray(JsonNumber(3))))
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonNumber 4 value" in new Context {
      parser.parseJsonObject( """{"key":[4]}""") === JsonObject(Map("key" -> JsonArray(JsonNumber(4))))
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonString value" in new Context {
      parser.parseJsonObject( """{"key":["str"]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str"))))
    }

    "construct AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      parser.parseJsonObject( """{"key":{}}""") === JsonObject(Map("key" -> JsonObject()))
    }

    "construct AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      parser.parseJsonObject( """{"key":{}}""") === JsonObject(Map("key" -> JsonObject()))
    }

    "construct AST correctly for two simple pairs of domain.JsonNumber as value" in new Context {
      parser.parseJsonObject( """{"first":1,"second":2}""") === JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonNumber(2)))
    }

    "construct AST correctly for two simple pairs of domain.JsonNumber and domain.JsonString as value" in new Context {
      parser.parseJsonObject( """{"first":1,"second":"2"}""") === JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonString("2")))
    }

    /*Recursion test*/

    /*Array single level*/
    "construct AST correctly for one simple pair of an Array with two JsonString's as values" in new Context {
      parser.parseJsonObject( """{"key":["str1","str2"]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonString("str2"))))
    }

    "construct AST correctly for one complex pair of an Array with two multipule values" in new Context {
      parser.parseJsonObject( """{"key":["str1",4,null,"str2"]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonNumber(4), JsonNull, JsonString("str2"))))
    }

    /*Array double level*/


    "construct AST correctly for one complex pair of an Array with an Inner Array as value" in new Context {
      parser.parseJsonObject( """{"key":["str1",["str2"]]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2")))))
    }

    "construct AST correctly for one complex pair of an Array with an Inner Array with as single value as value" in new Context {
      parser.parseJsonObject( """{"key":["str1",["str2"]]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2")))))
    }

    "construct AST correctly for one complex pair of an Array with an Inner Array with two values as value" in new Context {
      parser.parseJsonObject( """{"key":["str1",["str2",7]]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2"), JsonNumber(7)))))
    }

    "construct AST correctly for one complex pair of an Array with an Inner Array with two values which one of them is an object as value" in new Context {
      parser.parseJsonObject( """{"key":["str1",["str2",{"keyInner":"val"}]]}""") ===
        JsonObject(Map("key" -> JsonArray(JsonString("str1"),
          JsonArray(JsonString("str2"),
            JsonObject(Map("keyInner" -> JsonString("val")))))))
    }

    /*Object single level*/

    "construct AST correctly for one simple pair of an Object with one JsonObject with one pair as value" in new Context {
      parser.parseJsonObject( """{"key":{"keyInner":"valueInner"}}""") === JsonObject(Map("key" -> JsonObject(Map("keyInner" -> JsonString("valueInner")))))
    }

    "construct AST correctly for one simple pair of an Object with one JsonObject with two pairs as value" in new Context {
      parser.parseJsonObject( """{"key":{"keyInner":"valueInner","secondInner":null}}""") ===
        JsonObject(Map("key" ->
          JsonObject(Map("keyInner" -> JsonString("valueInner"),
            "secondInner" -> JsonNull))))
    }

    /*Object double level*/
    "construct AST correctly for one pair of an Object with an Inner Object as value that has another Inner Object" in new Context {
      parser.parseJsonObject( """{"key":{"keyInner":"valueInner","keyInnerObj":{"secondInner":null}}}""") ===
        JsonObject(Map("key" ->
          JsonObject(Map("keyInner" -> JsonString("valueInner"),
            "keyInnerObj" -> JsonObject(Map("secondInner" -> JsonNull))))))
    }

    "construct AST correctly for one pair of an Object with an Inner Object as value that has another Inner object with two elements" in new Context {
      parser.parseJsonObject( """{"key":{"keyInner":"valueInner","keyInnerObj":{"secondInner":null,"secondInner":7}}}""") ===
        JsonObject(Map("key" ->
          JsonObject(Map("keyInner" -> JsonString("valueInner"),
            "keyInnerObj" -> JsonObject(Map("secondInner" -> JsonNull, "secondInner" -> JsonNumber(7)))))))
    }

    "construct AST correctly for one pair of an Object with an Inner Object as value that has another Inner Array with multipul elements" in new Context {
      parser.parseJsonObject( """{"key":{"keyInner":"valueInner","keyInnerObj":["secondInner",null,"secondInner",7]}}""") ===
        JsonObject(Map("key" ->
          JsonObject(Map("keyInner" -> JsonString("valueInner"),
            "keyInnerObj" -> JsonArray(JsonString("secondInner"),
              JsonNull,
              JsonString("secondInner"),
              JsonNumber(7))))))
    }


  }
}
