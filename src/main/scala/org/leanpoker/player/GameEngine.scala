package org.leanpoker.player

import scala.collection.JavaConverters._

class GameEngine(game: Game) {

  def processGame: Int = {
    val estimation = CardsEstimator.estimateWithHand(myCards, game.community_cards.asScala.toList)

    if (estimation >= 0.8) {
      // good cards
      if (isAllIn) {
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
          if(estimation > 0.5 && shouldCall){
            call
          } else {
            if(shouldCall) {
              minimumRaise
            } else {
              0
            }
          }
        }
      }
    }
  }

  def shouldCall = {
    stack * 0.2 <= call
  }

  def estimate(stack: Int, estimated: Double, minimumRaise: Int, call: Int): Int = {
    val estimation = CardsEstimator.estimateWithHand(myCards, game.community_cards.asScala.toList)

    def shouldRaise: Boolean = {
      estimation >= 0.8
    }

    def shouldCall: Boolean = true
    def shouldFold: Boolean = true

    if (shouldRaise) {

    } else {
      if (shouldCall) {

      } else {
        if (shouldFold) {

        }
        else {

        }
      }
    }
    0
  }


  def myCards = {
    game.players.asScala(game.in_action).hole_cards.asScala.toList
  }

  def isAllIn = {
    minimumRaise >= stack
  }

  def stack = {
    game.players.asScala(game.in_action).stack
  }

  def call = {
    def bet(current_buy_in: Int, my_bet: Int): Int = {
      current_buy_in - my_bet
    }
    bet(game.current_buy_in, game.players.asScala(game.in_action).bet)
  }

  def minimumRaise = {
    def raise(current_buy_in: Int, my_bet: Int, minimum_raise: Int): Int = {
      current_buy_in - my_bet + minimum_raise
    }
    raise(game.current_buy_in, game.players.asScala(game.in_action).bet, game.minimum_raise)
  }
}
