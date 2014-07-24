package parser

import domain._

import scala.collection.immutable.Map
import scala.collection.mutable.ListBuffer

/**
 * Created by maximribakov on 7/22/14.
 */
object JsonParser {

  def parseJsonObject(blob: String): JsonObject = {
    val content: String = dropExpectedEdges(blob.trim, ('{', '}'))

    var pairs: Map[String, JsonValue] = Map.empty

    val separatedPairs = extractElements(content, ',')

    for (pair <- separatedPairs if !pair.isEmpty) {
      val (key, value) = parsePair(pair)
      pairs += (key -> value)
    }

    JsonObject(pairs)
  }

  def parseJsonArray(blob: String): JsonValue = {
    val content: String = dropExpectedEdges(blob.trim, ('[', ']'))

    var values = new ListBuffer[JsonValue]

    val separatedValues = extractElements(content, ',')

    for (value <- separatedValues if !value.isEmpty) {
      values += parseJsonValue(value)
    }

    JsonArray(values: _*)
  }

  private def extractElements(jsonPart: String, separator: Char) = {
    val splitPartialElementsByComma = jsonPart.split(separator)

    case class ElementsAccumulator(bracesDiff: Int = 0, accumulatedElement: String = "", elements: Seq[String] = Seq()) {
      def accumulate(partialElement: String) = {
        val newBracesDiff = this.bracesDiff + ((partialElement.count(_ == '[') - partialElement.count(_ == ']')) +
                                                  (partialElement.count(_ == '{') - partialElement.count(_ == '}')))
        if (newBracesDiff == 0)
          completeAccumulationOfElement(partialElement)
        else
          continueAccumulationOfElement(partialElement, newBracesDiff)
      }

      def completeAccumulationOfElement(curValue: String) = ElementsAccumulator(elements = elements :+ (accumulatedElement + curValue))
      def continueAccumulationOfElement(curValue: String, bracesDiff: Int) = ElementsAccumulator(bracesDiff, accumulatedElement + curValue + ",", elements)
    }

    splitPartialElementsByComma.foldLeft(ElementsAccumulator())(
      (accumulatorForFields, partialElement) => accumulatorForFields.accumulate(partialElement)).elements
  }


  def parsePair(blob: String): (String, JsonValue) = {
    val key = dropExpectedEdges(blob.trim.substring(0, blob.indexOf(':')), ('\"', '\"'))
    val value = parseJsonValue(blob.substring(blob.indexOf(':') + 1))
    (key, value)
  }

  def parseJsonValue(blob: String): JsonValue = {
    val onlyDigitsRegex = "^\\d*$".r

    blob match {
      case string if (string.head == '\"') => JsonString(dropExpectedEdges(string, ('\"', '\"')))
      case array if (array.head == '[') => parseJsonArray(array)
      case obj if (obj.head == '{') => parseJsonObject(obj)
      case "null" => JsonNull
      case "true" => JsonBoolean(true)
      case "false" => JsonBoolean(false)
      case onlyDigitsRegex() => JsonNumber(blob.toInt)
    }
  }

  private def dropExpectedEdges(blob: String, expectedEdges: (Char, Char)): String = {

    val leftEdge = blob.trim.head
    val rightEdge = blob.trim.reverse.head

    if (expectedEdges !=(leftEdge, rightEdge))
      throw new JsonFormatException("expected edges of element are : " + expectedEdges.toString() + ". but was" + (leftEdge, rightEdge).toString())

    blob.trim.drop(1).dropRight(1)
  }
}