package org.leanpoker.player

import com.google.gson.{Gson, JsonElement}
import scala.collection.JavaConverters._
import java.{util => ju}

object Player {
  val VERSION = "Funny Chicken 5"

  var game: Game = _

  def betRequest(request: JsonElement) = {
    val gson = new Gson
    game = gson.fromJson(request, classOf[Game])
    requestGame(game)
  }

  def requestGame(game: Game) = {
    val shouldRaise = true
    if(shouldRaise){
      rasingGame(game)
    } else {
      25
    }

  }

  def myCards = {
    game.players.asScala(game.in_action).hole_cards.asScala.toList
  }

  def rasingGame(game: Game): Int = {
    def raise(current_buy_in : Int, my_bet: Int, minimum_raise: Int): Int = {
      current_buy_in - my_bet + minimum_raise
    }

    val minimumRaise = raise(game.current_buy_in, game.players.asScala(game.in_action).bet, game.minimum_raise)
    val estimation = CardsEstimator.estimate(game.community_cards.asScala.toList ++ myCards)

    if(estimation >= 0.8) { // good cards
      if(minimumRaise >= game.players.asScala(game.in_action).stack) {
        // all in
        minimumRaise
      } else {
        (minimumRaise * (1 + estimation)).toInt
      }
    } else {
      if (estimation < 0.3) { // bad cards
        0
      } else { // ok cards
        minimumRaise
      }
    }
  }



  def showdown(game: JsonElement) {

  }
}

case class Card(rank: String, suit: String)

case class Player(name: String, stack: Int, status: String, bet: Int, hole_cards: ju.List[Card], version: String, id: Int)

case class Game(players: ju.List[Player], tournament_id: String, game_id: String, round: Int, bet_index: Int, small_blind: Int, orbits: Int, dealer: Int, community_cards: ju.List[Card], current_buy_in: Int, pot: Int, minimum_raise: Int, in_action: Int)
