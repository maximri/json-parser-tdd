package domain

import scala.collection.immutable.Map

/**
 * Created by maximribakov on 7/22/14.
 */
case class JsonObject(pairs : Map[String,JsonValue] = Map.empty) extends JsonValue {

}
