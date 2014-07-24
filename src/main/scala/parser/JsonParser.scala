package parser

import domain._

/**
 * Created by maxim ribakov on 7/22/14.
 */
object JsonParser {

  def parseJsonObject(blob: String): JsonObject = {
    JsonObject(parseCompoundJsonElement(blob, ('{', '}'), parsePair).toMap)
  }

  def parseJsonArray(blob: String): JsonValue = {
    JsonArray(parseCompoundJsonElement(blob, ('[', ']'), parseJsonValue): _*)
  }

  private def parseCompoundJsonElement[T](blob: String, expectedEdges: (Char, Char), elementParser: String => T) = {
    val content = dropExpectedEdges(blob.trim, expectedEdges)
    val separatedElements = extractElements(content, ',')

    separatedElements.filter(!_.isEmpty).map(elementParser)
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

  //TODO : remove after Shay review
  private def separatePairs(content: String, separator: Char): Seq[String] = {
    var pairs = Seq[String]()

    var partialBlob = ""
    var bracesBalanceCount = 0
    for (element <- content.split(separator)) {

      partialBlob = partialBlob + element

      bracesBalanceCount = bracesBalanceCount + ((element.count(_ == '[') - element.count(_ == ']')) +
        (element.count(_ == '{') - element.count(_ == '}')))

      if (isSeparatedInsideByInnerElements) {
        partialBlob += separator
      }
      else {
        pairs = pairs :+ partialBlob
        partialBlob = ""
      }

      def isSeparatedInsideByInnerElements: Boolean = {
        bracesBalanceCount > 0
      }
    }

    pairs
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