package parser

import domain._

/**
 * Created by maximribakov on 7/22/14.
 */

object SampleData {

  case class JsonTuple(stringRep: String, jsonValue: JsonValue)

  val emptyObject = JsonTuple("{}", JsonObject())
  val objectWithEmptyArray = JsonTuple( """{"key":[]}""", JsonObject(Map("key" -> JsonArray())))
  val objectWithOneNumber = JsonTuple( """{"key":3}""", JsonObject(Map("key" -> JsonNumber(3))))
  val objectWithOneString = JsonTuple( """{"key":"3"}""", JsonObject(Map("key" -> JsonString("3"))))
  val objectWithOneNull = JsonTuple( """{"key":null}""", JsonObject(Map("key" -> JsonNull)))
  val objectWithOneBoolean = JsonTuple( """{"key":true}""", JsonObject(Map("key" -> JsonBoolean(true))))
  val objectWithAnArrayWithOneNumber = JsonTuple( """{"key":[3]}""", JsonObject(Map("key" -> JsonArray(JsonNumber(3)))))
  val objectWithAnArrayWithOneString = JsonTuple( """{"key":["str"]}""", JsonObject(Map("key" -> JsonArray(JsonString("str")))))
  val objectWithAnEmptyObjectInside = JsonTuple( """{"key":{}}""", JsonObject(Map("key" -> JsonObject())))
  val objectWithTwoSimplePairsWithNumbers = JsonTuple( """{"first":1,"second":2}""", JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonNumber(2))))
  val objectWithTwoSimplePairsOneNumberOneString = JsonTuple( """{"first":1,"second":"2"}""", JsonObject(Map("first" -> JsonNumber(1), "second" -> JsonString("2"))))

  val objectWithAnArrayWithTwoStrings = JsonTuple( """{"key":["str1","str2"]}""", JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonString("str2")))))
  val objectWithAnArrayWithMultiple = JsonTuple( """{"key":["str1",4,null,"str2"]}""", JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonNumber(4), JsonNull, JsonString("str2")))))

  val objectWithAnArrayWithOneStringAndSimpleInnerArray = JsonTuple( """{"key":["str1",["str2"]]}""", JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2"))))))
  val objectWithAnArrayWithOneStringAndInnerArrayWithTwoValues = JsonTuple( """{"key":["str1",["str2",7]]}""",
    JsonObject(Map("key" -> JsonArray(JsonString("str1"), JsonArray(JsonString("str2"), JsonNumber(7))))))
  val objectWithOneComplexArrayWithInnerArrayWithTwoValuesWhichOneIsObject = JsonTuple( """{"key":["str1",["str2",{"keyInner":"val"}]]}""",
    JsonObject(Map("key" -> JsonArray(JsonString("str1"),
      JsonArray(JsonString("str2"),
        JsonObject(Map("keyInner" -> JsonString("val"))))))))

  val objectWithInnerObjectWithOnePair = JsonTuple( """{"key":{"keyInner":"valueInner"}}""", JsonObject(Map("key" -> JsonObject(Map("keyInner" -> JsonString("valueInner"))))))

  val objectWithInnerObjectWithTwoPairs = JsonTuple( """{"key":{"keyInner":"valueInner","secondInner":null}}""",
    JsonObject(Map("key" ->
      JsonObject(Map("keyInner" -> JsonString("valueInner"),
        "secondInner" -> JsonNull)))))

  val objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithOneElement = JsonTuple( """{"key":{"keyInner":"valueInner","keyInnerObj":{"secondInner":null}}}""",
    JsonObject(Map("key" ->
      JsonObject(Map("keyInner" -> JsonString("valueInner"),
        "keyInnerObj" -> JsonObject(Map("secondInner" -> JsonNull)))))))


  val objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithTwoElements = JsonTuple( """{"key":{"keyInner":"valueInner","keyInnerObj":{"secondInner":null,"secondInner2":7}}}""",
    JsonObject(Map("key" ->
      JsonObject(Map("keyInner" -> JsonString("valueInner"),
        "keyInnerObj" -> JsonObject(Map("secondInner" -> JsonNull, "secondInner2" -> JsonNumber(7))))))))


  val objectWithInnerObjectWithTwoPairsWhichOneIsObjectWithMultipleElements = JsonTuple( """{"key":{"keyInner":"valueInner","keyInnerObj":["secondInner",null,"secondInner",7]}}""",
    JsonObject(Map("key" ->
      JsonObject(Map("keyInner" -> JsonString("valueInner"),
        "keyInnerObj" -> JsonArray(JsonString("secondInner"),
          JsonNull,
          JsonString("secondInner"),
          JsonNumber(7)))))))

}
