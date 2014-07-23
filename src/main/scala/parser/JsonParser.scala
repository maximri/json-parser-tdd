package parser

import domain._

import scala.collection.mutable.ListBuffer

/**
 * Created by maximribakov on 7/22/14.
 */
object JsonParser {

  def parseObject(blob: String): JsonObject = {
    val content: String = dropExpectedEdges(blob.trim, ('{', '}'))

    var pairs: Map[String, JsonValue] = Map.empty

    val separatedPairs = separatePairs(content, ',')

    for (pair <- separatedPairs if !pair.isEmpty) {
      val (key: String, value: JsonValue) = parsePair(pair)
      pairs += (key -> value)
    }

    JsonObject(pairs)
  }

  private def separatePairs(content: String, separator: Char): Seq[String] = {
    var pairs: Seq[String] = Seq[String]()

    var partialBlob = ""
    var bracesBalanceCount = 0
    for (element <- content.split(separator)) {

      partialBlob = partialBlob + element

      bracesBalanceCount = bracesBalanceCount + (element.count(_ == '[') - element.count(_ == ']'))

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

  def parsePair(pair: String): (String, JsonValue) = {
    val splitByDotDot: Array[String] = pair.split(':')
    val key = dropExpectedEdges(splitByDotDot(0), ('\"', '\"'))
    val value = parseJsonValue(splitByDotDot(1))
    (key, value)
  }

  def parseJsonArray(blob: String): JsonValue = {
    val content: String = dropExpectedEdges(blob, ('[', ']'))

    var values = new ListBuffer[JsonValue]

    val splitByComma: Array[String] = content.split(',')

    for (value <- splitByComma if !value.isEmpty) {
      values += parseJsonValue(value)
    }

    JsonArray(values: _*)
  }

  def parseJsonValue(blob: String): JsonValue = {
    val onlyDigitsRegex = "^\\d*$".r

    blob match {
      case string if (string.head == '\"') => JsonString(dropExpectedEdges(string, ('\"', '\"')))
      case array if (array.head == '[') => parseJsonArray(array)
      case obj if (obj.head == '{') => JsonObject()
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