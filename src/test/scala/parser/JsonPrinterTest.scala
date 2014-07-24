package printer

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import parser.JsonPrinter
import parser.SampleData._

/**
 * Created by maximribakov on 7/23/14.
 */
class JsonPrinterTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val printer = JsonPrinter
  }

  "JsonPrinter" should {

    "print AST correctly for an empty string" in new Context {
      printer.printJsonValue(emptyObject.jsonValue) === emptyObject.stringRep
    }

    "print AST correctly for one simple pair of domain.JsonNumber as value" in new Context {
      printer.printJsonValue(objectWithOneNumber.jsonValue) === objectWithOneNumber.stringRep
    }

    "print AST correctly for one simple pair of domain.JsonString as value" in new Context {
      printer.printJsonValue(objectWithOneString.jsonValue) === objectWithOneString.stringRep
    }

    "print AST correctly for one simple pair of JsonNull as value" in new Context {
      printer.printJsonValue(objectWithOneNull.jsonValue) === objectWithOneNull.stringRep
    }

    "print AST correctly for one simple pair of domain.JsonBoolean as value" in new Context {
      printer.printJsonValue(objectWithOneBoolean.jsonValue) === objectWithOneBoolean.stringRep
    }

    "print AST correctly for one simple pair of empty Array as value" in new Context {
      printer.printJsonValue(objectWithEmptyArray.jsonValue) === objectWithEmptyArray.stringRep
    }

    "print AST correctly for one simple pair of an Array with domain.JsonNumber 3 value" in new Context {
      printer.printJsonValue(objectWithAnArrayWithOneNumber.jsonValue) === objectWithAnArrayWithOneNumber.stringRep
    }

    "print AST correctly for one simple pair of an Array with domain.JsonString value" in new Context {
      printer.printJsonValue(objectWithAnArrayWithOneString.jsonValue) === objectWithAnArrayWithOneString.stringRep
    }

    "print AST correctly for one simple pair of an empty domain.JsonObject as value" in new Context {
      printer.printJsonValue(objectWithAnEmptyObjectInside.jsonValue) === objectWithAnEmptyObjectInside.stringRep
    }

    "print AST correctly for two simple pairs of domain.JsonNumber as value" in new Context {
      printer.printJsonValue(objectWithTwoSimplePairsWithNumbers.jsonValue) === objectWithTwoSimplePairsWithNumbers.stringRep
    }

    "print AST correctly for two simple pairs of domain.JsonNumber and domain.JsonString as value" in new Context {
      printer.printJsonValue(objectWithTwoSimplePairsOneNumberOneString.jsonValue) === objectWithTwoSimplePairsOneNumberOneString.stringRep
    }

    /*Recursion test*/

    /*Array single level*/
    "print AST correctly for one complex pair of an Array with two JsonString's as values" in new Context {
      printer.printJsonValue(objectWithAnArrayWithTwoStrings.jsonValue) === objectWithAnArrayWithTwoStrings.stringRep
    }

    "print AST correctly for one complex pair of an Array with two multipule values" in new Context {
      printer.printJsonValue(objectWithAnArrayWithMultiple.jsonValue) === objectWithAnArrayWithMultiple.stringRep
    }

    /*Array double level*/


    "print AST correctly for one complex pair of an Array with an Inner Array as value" in new Context {
      printer.printJsonValue(objectWithAnArrayWithOneStringAndSimpleInnerArray.jsonValue) === objectWithAnArrayWithOneStringAndSimpleInnerArray.stringRep
    }

    "print AST correctly for one complex pair of an Array with an Inner Array with two values as value" in new Context {
      printer.printJsonValue(objectWithAnArrayWithOneStringAndInnerArrayWithTwoValues.jsonValue) === objectWithAnArrayWithOneStringAndInnerArrayWithTwoValues.stringRep
    }

    "print AST correctly for one complex pair of an Array with an Inner Array with two values which one of them is an object as value" in new Context {
      printer.printJsonValue(objectWithOneComplexArrayWithInnerArrayWithTwoValuesWhichOneIsObject.jsonValue) === objectWithOneComplexArrayWithInnerArrayWithTwoValuesWhichOneIsObject.stringRep
    }
    /*Object single level*/

    "print AST correctly for one simple pair of an Object with one JsonObject with one pair as value" in new Context {
      printer.printJsonValue(objectWithInnerObjectWithOnePair.jsonValue) === objectWithInnerObjectWithOnePair.stringRep
    }

    "print AST correctly for one simple pair of an Object with one JsonObject with two pairs as value" in new Context {
      printer.printJsonValue(objectWithInnerObjectWithTwoPairs.jsonValue) === objectWithInnerObjectWithTwoPairs.stringRep
    }

    /*Object double level*/
    "print AST correctly for one pair of an Object with an Inner Object as value that has another Inner Object" in new Context {
      printer.printJsonValue(objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithOneElement.jsonValue) === objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithOneElement.stringRep
    }

    "print AST correctly for one pair of an Object with an Inner Object as value that has another Inner object with two elements" in new Context {
      printer.printJsonValue(objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithTwoElements.jsonValue) === objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithTwoElements.stringRep
    }

    "print AST correctly for one pair of an Object with an Inner Object as value that has another Inner Array with multipul elements" in new Context {
      printer.printJsonValue(objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithMultipleElements.jsonValue) === objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithMultipleElements.stringRep
    }
  }

}
