package org.leanpoker.player

import com.google.gson.JsonParser
import org.scalatest.{FunSpec, MustMatchers}
import scala.collection.JavaConverters._

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
    Player.betRequest(jsonElement) must be(25)
  }

  it("should parse request - 2") {
    val jsonElement = new JsonParser()
      .parse("{\n  \"players\":[\n    {\n      \"name\":\"Player 1\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 1\",\n      \"id\":0\n    },\n    {\n      \"name\":\"Player 2\",\n      \"stack\":1000,\n      \"status\":\"active\",\n      \"bet\":0,\n      \"hole_cards\":[],\n      \"version\":\"Version name 2\",\n      \"id\":1\n    }\n  ],\n  \"tournament_id\":\"550d1d68cd7bd10003000003\",\n  \"game_id\":\"550da1cb2d909006e90004b1\",\n  \"round\":0,\n  \"bet_index\":0,\n  \"small_blind\":10,\n  \"orbits\":0,\n  \"dealer\":0,\n  \"community_cards\":[],\n  \"current_buy_in\":0,\n  \"pot\":0\n}")
    Player.betRequest(jsonElement)
    Player.game.players.asScala.head.stack must be(1000)
  }

}