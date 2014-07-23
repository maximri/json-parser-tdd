package parser


import domain._
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

class JsonParserStreamTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val parser = JsonParserStream
  }

  "JsonParserStream" should {
    "construct AST correctly for an empty string" in new Context {
      parser.parseJsonObject("{}") === JsonObject()
    }
  }
}