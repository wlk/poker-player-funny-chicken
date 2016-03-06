package org.leanpoker.player

import com.google.gson.{Gson, JsonElement}
import scala.collection.JavaConverters._
import java.{util => ju}

object Player {
  val VERSION = "Funny Chicken 2"

  var game: Game = _

  def betRequest(request: JsonElement) = {
    val gson = new Gson
    game = gson.fromJson(request, classOf[Game])
    requestGame(game)
  }

  def requestGame(game: Game) = {
    25
  }

  def showdown(game: JsonElement) {

  }
}

case class Card(rank: String, suit: String)

case class Player(name: String, stack: Int, status: String, bet: Int, hole_cards: ju.List[Card], version: String, id: Int)

case class Game(players: ju.List[Player], tournament_id: String, game_id: String, round: Int, bet_index: Int, small_blind: Int, orbits: Int, dealer: Int, community_cards: ju.List[Card], current_buy_in: Int, pot: Int, minimum_raise: Int, in_action: Int)
