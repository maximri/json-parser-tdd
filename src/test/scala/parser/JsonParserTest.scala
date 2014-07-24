package parser

import data.SampleData
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope


class JsonParserTest extends SpecificationWithJUnit {

  import SampleData._

  trait Context extends Scope {
    val parser = JsonParser
  }

  "JsonParser" should {

    "construct AST correctly for an empty string" in new Context {
      parser.parseJsonObject(emptyObject.stringRep) === emptyObject.jsonValue
    }

    "construct AST correctly for one simple pair of domain.JsonNumber as value" in new Context {
      parser.parseJsonObject(objectWithOneNumber.stringRep) === objectWithOneNumber.jsonValue
    }

    "construct AST correctly for one simple pair of domain.JsonString as value" in new Context {
      parser.parseJsonObject(objectWithOneString.stringRep) === objectWithOneString.jsonValue
    }

    "construct AST correctly for one simple pair of JsonNull as value" in new Context {
      parser.parseJsonObject(objectWithOneNull.stringRep) === objectWithOneNull.jsonValue
    }

    "construct AST correctly for one simple pair of domain.JsonBoolean as value" in new Context {
      parser.parseJsonObject(objectWithOneBoolean.stringRep) === objectWithOneBoolean.jsonValue
    }

    "construct AST correctly for one simple pair of empty Array as value" in new Context {
      parser.parseJsonObject(objectWithEmptyArray.stringRep) === objectWithEmptyArray.jsonValue
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonNumber 3 value" in new Context {
      parser.parseJsonObject(objectWithAnArrayWithOneNumber.stringRep) === objectWithAnArrayWithOneNumber.jsonValue
    }

    "construct AST correctly for one simple pair of an Array with domain.JsonString value" in new Context {
      parser.parseJsonObject(objectWithAnArrayWithOneString.stringRep) === objectWithAnArrayWithOneString.jsonValue
    }

    "construct AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      parser.parseJsonObject(objectWithAnEmptyObjectInside.stringRep) === objectWithAnEmptyObjectInside.jsonValue
    }

    "construct AST correctly for two simple pairs of domain.JsonNumber as value" in new Context {
      parser.parseJsonObject(objectWithTwoSimplePairsWithNumbers.stringRep) === objectWithTwoSimplePairsWithNumbers.jsonValue
    }

    "construct AST correctly for two simple pairs of domain.JsonNumber and domain.JsonString as value" in new Context {
      parser.parseJsonObject(objectWithTwoSimplePairsOneNumberOneString.stringRep) === objectWithTwoSimplePairsOneNumberOneString.jsonValue
    }

    /*Recursion test*/

    /*Array single level*/
    "construct AST correctly for one complex pair of an Array with two JsonString's as values" in new Context {
      parser.parseJsonObject(objectWithAnArrayWithTwoStrings.stringRep) === objectWithAnArrayWithTwoStrings.jsonValue
    }

    "construct AST correctly for one complex pair of an Array with two multipule values" in new Context {
      parser.parseJsonObject(objectWithAnArrayWithMultiple.stringRep) === objectWithAnArrayWithMultiple.jsonValue
    }

    /*Array double level*/


    "construct AST correctly for one complex pair of an Array with an Inner Array as value" in new Context {
      parser.parseJsonObject(objectWithAnArrayWithOneStringAndSimpleInnerArray.stringRep) === objectWithAnArrayWithOneStringAndSimpleInnerArray.jsonValue
    }

    "construct AST correctly for one complex pair of an Array with an Inner Array with two values as value" in new Context {
      parser.parseJsonObject(objectWithAnArrayWithOneStringAndInnerArrayWithTwoValues.stringRep) === objectWithAnArrayWithOneStringAndInnerArrayWithTwoValues.jsonValue
    }

    "construct AST correctly for one complex pair of an Array with an Inner Array with two values which one of them is an object as value" in new Context {
      parser.parseJsonObject(objectWithOneComplexArrayWithInnerArrayWithTwoValuesWhichOneIsObject.stringRep) === objectWithOneComplexArrayWithInnerArrayWithTwoValuesWhichOneIsObject.jsonValue
    }
    /*Object single level*/

    "construct AST correctly for one simple pair of an Object with one JsonObject with one pair as value" in new Context {
      parser.parseJsonObject(objectWithInnerObjectWithOnePair.stringRep) === objectWithInnerObjectWithOnePair.jsonValue
    }

    "construct AST correctly for one simple pair of an Object with one JsonObject with two pairs as value" in new Context {
      parser.parseJsonObject(objectWithInnerObjectWithTwoPairs.stringRep) === objectWithInnerObjectWithTwoPairs.jsonValue
    }

    /*Object double level*/
    "construct AST correctly for one pair of an Object with an Inner Object as value that has another Inner Object" in new Context {
      parser.parseJsonObject(objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithOneElement.stringRep) === objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithOneElement.jsonValue
    }

    "construct AST correctly for one pair of an Object with an Inner Object as value that has another Inner object with two elements" in new Context {
      parser.parseJsonObject(objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithTwoElements.stringRep) === objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithTwoElements.jsonValue
    }

    "construct AST correctly for one pair of an Object with an Inner Object as value that has another Inner Array with multipul elements" in new Context {
      parser.parseJsonObject(objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithMultipleElements.stringRep) === objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithMultipleElements.jsonValue
    }
  }
}
