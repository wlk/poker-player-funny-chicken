package org.leanpoker.player

object CardsEstimator {
  val mappings: Map[(Int, Int, Boolean), Int] = Map(
    (1, 1, false) -> 7,
    (2, 2, false) -> 7,
    (3, 3, false) -> 7,
    (4, 4, false) -> 6,
    (5, 5, false) -> 6,
    (6, 6, false) -> 5,
    (7, 7, false) -> 4,
    (8, 8, false) -> 3,
    (9, 9, false) -> 2,
    (10, 10, false) -> 1,
    (11, 11, false) -> 1,
    (12, 12, false) -> 1,
    (13, 13, false) -> 1,
    (13, 12, false) -> 2,
    (13, 11, false) -> 3,
    (13, 10, false) -> 4,
    (13, 9, false) -> 6,
    (13, 8, false) -> 8,
    (12, 11, false) -> 4,
    (12, 10, false) -> 5,
    (12, 9, false) -> 6,
    (12, 8, false) -> 8,
    (11, 10, false) -> 5,
    (11, 9, false) -> 6,
    (11, 8, false) -> 8,
    (10, 9, false) -> 5,
    (10, 8, false) -> 7,
    (10, 7, false) -> 8,
    (9, 8, false) -> 7,
    (9, 7, false) -> 8,
    (8, 7, false) -> 7,
    (7, 6, false) -> 8,
    (6, 5, false) -> 8,
    (5, 4, false) -> 8,
    (4, 3, false) -> 8,
    (13, 12, true) -> 1,
    (13, 11, true) -> 2,
    (13, 10, true) -> 2,
    (13, 9, true) -> 3,
    (13, 8, true) -> 5,
    (13, 7, true) -> 5,
    (13, 6, true) -> 5,
    (13, 5, true) -> 5,
    (13, 4, true) -> 5,
    (13, 3, true) -> 5,
    (13, 2, true) -> 5,
    (13, 1, true) -> 5,
    (12, 11, true) -> 2,
    (12, 10, true) -> 3,
    (12, 9, true) -> 4,
    (12, 8, true) -> 6,
    (12, 7, true) -> 7,
    (12, 6, true) -> 7,
    (12, 5, true) -> 7,
    (12, 4, true) -> 7,
    (12, 3, true) -> 7,
    (12, 2, true) -> 7,
    (12, 1, true) -> 7,
    (11, 10, true) -> 3,
    (11, 9, true) -> 4,
    (11, 8, true) -> 5,
    (11, 7, true) -> 7,
    (10, 9, true) -> 3,
    (10, 8, true) -> 4,
    (10, 7, true) -> 5,
    (10, 6, true) -> 7,
    (9, 8, true) -> 4,
    (9, 7, true) -> 5,
    (9, 6, true) -> 7,
    (8, 7, true) -> 4,
    (8, 6, true) -> 5,
    (8, 5, true) -> 8,
    (7, 6, true) -> 5,
    (7, 5, true) -> 6,
    (7, 4, true) -> 8,
    (6, 5, true) -> 5,
    (6, 4, true) -> 6,
    (6, 3, true) -> 7,
    (5, 4, true) -> 7,
    (5, 3, true) -> 7,
    (4, 3, true) -> 6,
    (4, 2, true) -> 7,
    (3, 2, true) -> 7,
    (3, 1, true) -> 8,
    (2, 1, true) -> 8
  )

  def estimateWithHand(hand: List[Card], table: List[Card]): Double = {
    if (table.isEmpty) {
      val pos1 = getPosition(hand.head)
      val pos2 = getPosition(hand(1))
      val areSameColor = hand.head.suit == hand(1).suit

      val result = mappings.get((Math.max(pos1, pos2), Math.min(pos1, pos2), areSameColor)) match {
        case Some(rank) => rank
        case None => 0
      }

      if (result > 4) {
        1
      } else {
        0
      }

    } else {
      Math.min(estimate(hand ++ table, table) + handPoints(hand), 1)
    }
  }

  def estimate(l: List[Card], table: List[Card]): Double = {

    if (weHaveBetterPairs(l, table)) {
      0.9
    } else if (hasColor(l)) {
      0.95
    } else if (weHaveSecondPair(l, table)) {
      0.93
    }
    else {
      countPoints(l)
    }

  }

  def hasColor(l: List[Card]): Boolean = {
    l.groupBy(_.suit).mapValues(_.size).exists(g => g._2 > 3)
  }

  def countPoints(l: List[Card]): Double = {
    var good = 0.2
    for {
      c <- l
    } yield {
      c.rank match {
        case "A" => good += 0.05
        case "K" => good += 0.04
        case "Q" => good += 0.03
        case "J" => good += 0.02
        case "10" => good += 0.01
        case _ => good
      }
    }
    good
  }

  def weHaveBetterPairs(l: List[Card], table: List[Card]): Boolean = {
    countNumberOfRanks(l) > countNumberOfRanks(table)
  }

  def countNumberOfRanks(l: List[Card]): Int = {
    l.groupBy(_.rank).mapValues(_.size).maxBy(f => f._2)._2
  }

  def weHaveSecondPair(l: List[Card], table: List[Card]): Boolean = {
    val ourPairs = numberOfPairs(l)
    val tablePairs = numberOfPairs(table)
    ourPairs == 2 && tablePairs < 2
  }

  def numberOfPairs(l: List[Card]): Int = {
    l.groupBy(_.rank).mapValues(_.size).count(x => x._2 == 2)
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
      case _ => 0
    }
  }

  def handPoints(l: List[Card]): Double = {
    val card1 = l.head
    val card2 = l(1)
    if (card1.rank.equals(card2.rank) && getPosition(card1) >= 7) {
      if (getPosition(card1) >= 10) {
        0.2
      }
      else {
        0.1
      }
    }
    else {
      getPointsFromCard(card1) + getPointsFromCard(card2) /* + howFar(card1, card2) */ + sameColor(card1, card2)
    }
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

  def sameColor(card1: Card, card2: Card): Double = {
    if (card1.suit.equals(card2.suit)) {
      0.03
    }
    else {
      0.0
    }
  }

  def hasPair(l: List[Card]): Boolean = {
    for {
      c1i <- l.indices
      c2i <- c1i + 1 until l.size
    } yield {
      if (l(c1i).rank.equals(l(c2i).rank)) {
        true
      }
    }
    false
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
}