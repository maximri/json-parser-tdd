package domain

/**
 * Created by maximribakov on 7/22/14.
 */
case class JsonObject(pairs : Map[String,JsonValue] = Map()) extends JsonValue {

}
