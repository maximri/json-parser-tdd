package domain

/**
 * Created by maximribakov on 7/22/14.
 */
case class JsonArray(values : JsonValue*) extends JsonValue{

}

object JsonArray {
  def empty  = JsonArray()
}
