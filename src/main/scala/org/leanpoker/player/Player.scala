package org.leanpoker.player

import com.google.gson.{Gson, JsonElement}
import java.{util => ju}

object Player {
  val VERSION = "Funny Chicken 23"

  var game: Game = _

  def betRequest(request: JsonElement) = {
    try {
      val gson = new Gson
      game = gson.fromJson(request, classOf[Game])
      requestGame(game)
    } catch {
      case e: Throwable =>
        println("error" + e)
        0
    }
  }

  def requestGame(game: Game) = {
    val engine = new GameEngine(game)
    engine.processGame
  }

  def showdown(game: JsonElement) {

  }
}

case class Card(rank: String, suit: String)

case class Player(name: String, stack: Int, status: String, bet: Int, hole_cards: ju.List[Card], version: String, id: Int)

case class Game(players: ju.List[Player], tournament_id: String, game_id: String, round: Int, bet_index: Int, small_blind: Int, orbits: Int, dealer: Int, community_cards: ju.List[Card], current_buy_in: Int, pot: Int, minimum_raise: Int, in_action: Int)
