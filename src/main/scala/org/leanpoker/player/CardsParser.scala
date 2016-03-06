package org.leanpoker.player

/**
  * Created by rembam on 06.03.16.
  */
class CardsParser {
  
}

clubs,spades,hearts,diamonds
2-10 and J,Q,K,A


def listOfPossibile(l : List[Card]) : Double {
  var good = 20.0
  for(Card c : l) {
	 for(Card c1 : l) {
	  if (c1.rank.equals(c2.rank))
		80.0
  	}
	c.rank match {
		case "A" => good += 5
		case "K" => good += 4
		case "Q" => good += 3
		case "J" => good += 2
		case "10" => good += 1
	}
  }
}	
