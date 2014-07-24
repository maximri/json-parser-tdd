package printer

import domain._

/**
 * Created by maximribakov on 7/23/14.
 */
object JsonPrinter {
  def printJsonArray(array: JsonArray) = array.values.map(printJsonValue(_)).mkString("[", ",", "]")

  def printJsonValue(value: JsonValue): String = value match {
    case array: JsonArray => printJsonArray(array)
    case obj: JsonObject => printJsonObject(obj)
    case num: JsonNumber => printJsonNumber(num)
    case boolean: JsonBoolean => printJsonBoolean(boolean)
    case string: JsonString => printJsonString(string)
    case _ => printJsonNull
  }

  def printJsonObject(obj: JsonObject): String = obj.pairs.map(pair => ("\"" + pair._1 + "\":" + printJsonValue(pair._2))).mkString("{", ",", "}")

  def printJsonString(str: JsonString) = "\"" + str.value + "\""

  def printJsonNumber(num: JsonNumber) =  num.value.toString

  def printJsonBoolean(boolean: JsonBoolean) = boolean.value.toString

  def printJsonNull() = "null"


}
