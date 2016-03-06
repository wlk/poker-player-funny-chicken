package org.leanpoker.player

object CardsEstimator {
  def estimateWithHand(hand : List[Card], table: List[Card]): Double = {
    if (table.isEmpty) {
      Math.min(handPoints(hand)*5, 1)
    } else {
      Math.min(estimate(hand ++ table) + handPoints(hand), 1)
    }
  }

  def estimate(l: List[Card]): Double = {
    var good = 0.2
    for {
      c1i <- l.indices
      c2i <- c1i + 1 until l.size
    } yield {
      if (l(c1i).rank.equals(l(c2i).rank)) {
        good = 0.9
        return good
      }
    }


    for {
      c <- l
    } yield {
      c.rank match {
        case "A" => good += 0.05
        case "K" => good += 0.04
        case "Q" => good += 0.03
        case "J" => good += 0.02
        case "10" => good += 0.01
      }
    }
    good
  }

  def getPointsFromCard(card1: Card): Double = {
    card1.rank match {
      case "A" => 0.06
      case "K" => 0.05
      case "Q" => 0.04
      case "J" => 0.03
      case "10" => 0.02
      case "9" => 0.02
      case "8" => 0.01
      case "7" => 0.01
      case _ => 0.0
    }
  }

  def getPosition(card: Card): Int = {
    card.rank match {
      case "2" => 1
      case "3" => 2
      case "4" => 3
      case "5" => 4
      case "6" => 5
      case "7" => 6
      case "8" => 7
      case "9" => 8
      case "10" => 9
      case "J" => 10
      case "Q" => 11
      case "K" => 12
      case "A" => 13
    }
  }

  def howFar(card1: Card, card2: Card): Double = {
    val position1: Int = getPosition(card1)
    val position2: Int = getPosition(card2)
    val diff = 5 - Math.abs(position2 - position1)
    if (diff < 0) {
      0
    } else {
      diff * 0.01
    }
  }

  def handPoints(l: List[Card]): Double = {
    val card1 = l.head
    val card2 = l(1)
    if(card1.rank.equals(card2.rank))
      0.2
    else
    getPointsFromCard(card1) + getPointsFromCard(card2) + howFar(card1, card2)
  }
}