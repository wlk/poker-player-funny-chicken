package org.leanpoker.player

import com.google.gson.JsonParser
import org.scalatest.{FunSpec, MustMatchers}
import scala.collection.JavaConverters._
import java.{util => ju}

class PlayerTest extends FunSpec with MustMatchers {

  it("should parse request") {
    val jsonElement = new JsonParser()
      .parse("{\n  \"players\":[\n    {\n      \"name\":\"Player 1\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 1\",\n      \"id\":0\n    },\n    {\n      \"name\":\"Player 2\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 2\",\n      \"id\":1\n    }\n  ],\n  \"tournament_id\":\"550d1d68cd7bd10003000003\",\n  \"game_id\":\"550da1cb2d909006e90004b1\",\n  \"round\":0,\n  \"bet_index\":0,\n  \"small_blind\":10,\n  \"orbits\":0,\n  \"dealer\":0,\n  \"community_cards\":[],\n  \"current_buy_in\":0,\n  \"pot\":0\n}")
    Player.betRequest(jsonElement) must be(0)
  }

  it("should not raise") {
    val jsonElement = new JsonParser()
      .parse("{\n  \"players\":[\n    {\n      \"name\":\"Player 1\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 1\",\n      \"id\":0\n    },\n    {\n      \"name\":\"Player 2\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 2\",\n      \"id\":1\n    }\n  ],\n  \"tournament_id\":\"550d1d68cd7bd10003000003\",\n  \"game_id\":\"550da1cb2d909006e90004b1\",\n  \"round\":0,\n  \"bet_index\":0,\n  \"small_blind\":10,\n  \"orbits\":0,\n  \"dealer\":0,\n  \"community_cards\":[],\n  \"current_buy_in\":0,\n  \"pot\":0\n}")
    Player.betRequest(jsonElement) must be(0)
  }

  it("should raise by minimum") {
    val jsonElement = new JsonParser()
      .parse("{\n  \"players\":[\n    {\n      \"name\":\"Player 1\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 1\",\n      \"id\":0\n    },\n    {\n      \"name\":\"Player 2\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 2\",\n      \"id\":1\n    }\n  ],\n  \"tournament_id\":\"550d1d68cd7bd10003000003\",\n  \"game_id\":\"550da1cb2d909006e90004b1\",\n  \"round\":0,\n  \"bet_index\":0,\n  \"small_blind\":10,\n  \"orbits\":0,\n  \"dealer\":0,\n  \"community_cards\":[],\n  \"current_buy_in\":0,\n  \"pot\":0\n,\n  \"minimum_raise\":25}")
    Player.betRequest(jsonElement) must be(0)
  }

  it("should parse request - 2") {
    val jsonElement = new JsonParser()
      .parse("{\n  \"players\":[\n    {\n      \"name\":\"Player 1\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 1\",\n      \"id\":0\n    },\n    {\n      \"name\":\"Player 2\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 2\",\n      \"id\":1\n    }\n  ],\n  \"tournament_id\":\"550d1d68cd7bd10003000003\",\n  \"game_id\":\"550da1cb2d909006e90004b1\",\n  \"round\":0,\n  \"bet_index\":0,\n  \"small_blind\":10,\n  \"orbits\":0,\n  \"dealer\":0,\n  \"community_cards\":[],\n  \"current_buy_in\":0,\n  \"pot\":0\n}")
    Player.betRequest(jsonElement)
    Player.game.players.asScala.head.stack must be(1000)
  }

  it("good hand") {
    val cl: List[Card] = List(Card("A", "spades"), Card("K", "spades"))

    val points = CardsEstimator.handPoints(cl)

    points must be > 0.1
  }

  it("cards pair in hand empty table") {
    val cl: List[Card] = List(Card("Q", "spades"), Card("Q", "hearts"))
    val points = CardsEstimator.estimateWithHand(cl, List.empty)
    points must be > 0.9
  }

  it("cards pair in hand [3, 3] (weak pair)") {
    val cl: List[Card] = List(Card("3", "spades"), Card("3", "hearts"))
    val points = CardsEstimator.estimateWithHand(cl, List.empty)
    points must be < 0.7
  }

  it("cards in hand [3, 9]") {
    val cl: List[Card] = List(Card("3", "spades"), Card("9", "hearts"))
    val points = CardsEstimator.estimateWithHand(cl, List.empty)
    points must be < 0.3
  }

  it("cards pair in hand table [cQ, hK]") {
    val cl: List[Card] = List(Card("9", "spades"), Card("9", "hearts"))
    val points = CardsEstimator.estimateWithHand(cl, List(Card("Q", "clubs"), Card("K", "hearts")))
    points must be > 0.7
  }

  it("cards pair in hand table [c9, cQ, hK]") {
    val cl: List[Card] = List(Card("9", "spades"), Card("9", "hearts"))
    val points = CardsEstimator.estimateWithHand(cl, List(Card("9", "clubs"), Card("Q", "clubs"), Card("K", "hearts")))
    points must be > 0.8
  }

  it("cards pair in hand table [c9, h9, hK]") {
    val cl: List[Card] = List(Card("10", "spades"), Card("9", "hearts"))
    val points = CardsEstimator.estimateWithHand(cl, List(Card("9", "clubs"), Card("Q", "clubs"), Card("9", "hearts")))
    points must be > 0.8
  }

  it("super test") {

    val game = new Game(
      List(
        new Player("p1", 1000, "active", 0, List(Card("2", "spades"), Card("3", "hearts")).asJava, Player.VERSION, 0),
        new Player("p2", 1000, "active", 0, List(Card("7", "clubs"), Card("K", "clubs")).asJava, Player.VERSION, 1),
        new Player("p2", 1000, "active", 0, List(Card("8", "hearts"), Card("K", "hearts")).asJava, Player.VERSION, 2)
      ).asJava,
      "tid",
      "gid",
      4, // round
      0, // bet_index
      10, // small_blind
      1, // orbits
      1, // dealer
      new ju.ArrayList[Card](), // community_cards
      0, // current_buy_in
      1000, // pot
      10, // minimum_raise
      0) //in_action

    Player.requestGame(game) must be(0)
  }

  it("hasColor") {
    val cl : List[Card] = List(Card("A", "spades"), Card("K", "spades"), Card("Q", "spades"), Card("J", "spades"), Card("10", "spades") )
    CardsEstimator.hasColor(cl) must be (true)
  }

  it("noColor") {
    val cl : List[Card] = List(Card("A", "spades"), Card("K", "hearts"), Card("Q", "spades"), Card("J", "hearts"), Card("10", "spades") )
    CardsEstimator.hasColor(cl) must be (false)
  }

  it("badHand") {
    val cl : List[Card] = List(Card("2", "spades"), Card("3", "hearts"))

    CardsEstimator.estimateWithHand(cl, List.empty) must be (0.0)
  }

}