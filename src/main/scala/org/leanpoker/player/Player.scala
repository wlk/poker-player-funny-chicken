package org.leanpoker.player

import com.google.gson.{Gson, JsonElement}

object Player {

  val VERSION = "Funny Chicken"

  def betRequest(request: JsonElement) = {
    val gson = new Gson
    val game = gson.fromJson(request, classOf[GameState])
    10
  }

  def showdown(game: JsonElement) {

  }
}

case class Card(rank: String, suit: String)

case class Player(name: String, stack: Int, status: String, bet: Int, hole_cards: List[Card], version: String, id: Int)

case class GameState(players: List[Player], tournament_id: String, game_id: String, round: Int, bet_index: Int, small_blind: Int, orbits: Int, dealer: Int, community_cards: List[Card], current_buy_in: Int, pot: Int)