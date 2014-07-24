package domain

import scala.collection.immutable.Map

/**
 * Created by maximribakov on 7/22/14.
 */
sealed trait JsonValue

case class JsonString(value : String) extends JsonValue
case class JsonArray(values : JsonValue*) extends JsonValue

case class JsonObject(pairs : Map[String,JsonValue] = Map.empty) extends JsonValue
case class JsonNumber(value : Int) extends JsonValue
case class JsonBoolean(value: Boolean) extends JsonValue

object JsonNull extends JsonValue