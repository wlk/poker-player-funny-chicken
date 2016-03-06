package org.leanpoker.player

object CardsEstimator {
  def estimate(l: List[Card]): Double = {
    var good = 20.0
    for {
      c1 <- l
      c2 <- l
    } yield {
      if (c1.rank.equals(c2.rank)) {
        good = 90.0
        return good
      } else {
        c1.rank match {
          case "A" => good += 5
          case "K" => good += 4
          case "Q" => good += 3
          case "J" => good += 2
          case "10" => good += 1
        }
      }
    }
    good / 100.0
  }
}