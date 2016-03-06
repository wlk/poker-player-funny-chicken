package org.leanpoker.player

import scala.collection.JavaConverters._

class GameEngine(game: Game) {

  def processGame: Int = {
    val estimation = CardsEstimator.estimateWithHand(myCards, game.community_cards.asScala.toList)

    if (estimation >= 0.8) {
      // good cards
      if (minimumRaise >= game.players.asScala(game.in_action).stack) {
        // all in
        minimumRaise
      } else {
        (minimumRaise * (1 + estimation)).toInt
      }
    } else {
      if (estimation < 0.3) {
        // bad cards
        0
      } else {
        // ok cards
        if (isAllIn) {
          0
        } else {
          minimumRaise
        }
      }
    }
  }


  def myCards = {
    game.players.asScala(game.in_action).hole_cards.asScala.toList
  }

  def isAllIn = {
    minimumRaise >= game.players.asScala(game.in_action).stack
  }

  def minimumRaise = {
    def raise(current_buy_in: Int, my_bet: Int, minimum_raise: Int): Int = {
      current_buy_in - my_bet + minimum_raise
    }
    raise(game.current_buy_in, game.players.asScala(game.in_action).bet, game.minimum_raise)
  }
}
