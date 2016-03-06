package org.leanpoker.player

object CardsEstimator {
  def estimate(l: List[Card]): Double = {
    var good = 0.2
    for {
      c1 <- l
      c2 <- l
    } yield {
      if (c1.rank.equals(c2.rank)) {
        good = 0.9
        return good
      } else {
        c1.rank match {
          case "A" => good += 0.05
          case "K" => good += 0.04
          case "Q" => good += 0.03
          case "J" => good += 0.02
          case "10" => good += 0.01
        }
      }
    }
    good
  }
}