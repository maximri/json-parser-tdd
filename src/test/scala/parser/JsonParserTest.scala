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
      parser.parseObject("{}") === JsonObject()
    }

    "construct AST correctly for one simple pair of domain.JsonNumber as value" in new Context {
      parser.parseObject( """{"key":3}""") === JsonObject(Map("key" -> JsonNumber(3)))
    }

    "construct AST correctly for one simple pair of domain.JsonString as value" in new Context {
      parser.parseObject( """{"key":"3"}""") === JsonObject(Map("key" -> JsonString("3")))
    }

    "construct AST correctly for one simple pair of JsonNull as value" in new Context {
      parser.parseObject( """{"key":null}""") === JsonObject(Map("key" -> JsonNull))
    }

    "construct AST correctly for one simple pair of domain.JsonBoolean as value" in new Context {
      parser.parseObject( """{"key":true}""") === JsonObject(Map("key" -> JsonBoolean(true)))
    }

    "construct AST correctly for one simple pair of empty Array as value" in new Context {
      parser.parseObject( """{"key":[]}""") === JsonObject(Map("key" -> JsonArray()))
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonNumber 3 value" in new Context {
      parser.parseObject( """{"key":[3]}""") === JsonObject(Map("key" -> JsonArray(JsonNumber(3))))
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonNumber 4 value" in new Context {
      parser.parseObject( """{"key":[4]}""") === JsonObject(Map("key" -> JsonArray(JsonNumber(4))))
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonString value" in new Context {
      parser.parseObject( """{"key":["str"]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str"))))
    }

    "construct AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      parser.parseObject( """{"key":{}}""") === JsonObject(Map("key" -> JsonObject()))
    }

    "construct AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      parser.parseObject( """{"key":{}}""") === JsonObject(Map("key" -> JsonObject()))
    }

    "construct AST correctly for two simple pairs of domain.JsonNumber as value" in new Context {
      parser.parseObject( """{"first":1,"second":2}""") === JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonNumber(2)))
    }

    "construct AST correctly for two simple pairs of domain.JsonNumber and domain.JsonString as value" in new Context {
      parser.parseObject( """{"first":1,"second":"2"}""") === JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonString("2")))
    }


    "construct AST correctly for one simple pair of an Array with two JsonString's as values" in new Context {
      parser.parseObject( """{"key":["str1","str2"]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"),JsonString("str2"))))
    }


    "construct AST correctly for one simple pair of an Array with two JsonString's as values" in new Context {
      parser.parseObject( """{"key":["str1",4,null,"str2"]}""") === JsonObject(Map("key" -> JsonArray(JsonString("str1"),JsonNumber(4),JsonNull,JsonString("str2"))))
    }

  }
}
