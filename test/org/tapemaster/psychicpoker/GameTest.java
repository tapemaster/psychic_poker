package org.tapemaster.psychicpoker;

import static org.junit.Assert.assertEquals;
import static org.tapemaster.psychicpoker.PsychicPoker.cardInputHelper;

import org.junit.Test;

/**
 * Unit test for Game class.
 */
public class GameTest {

    @Test
    public void testGameHandVariations() {
        check("TH JH QC QD QS", "QH KH AH 2S 6S",
                Hand.Value.STRAIGHT_FLUSH);
        check("2H 2S 3H 3S 3C", "2D 3D 6C 9C TH",
                Hand.Value.FOUR_OF_A_KIND);
        check("2H 2S 3H 3S 3C", "2D 9C 3D 6C TH",
                Hand.Value.FULL_HOUSE);
        check("2H AD 5H AC 7H", "AH 6H 9H 4H 3C", 
                Hand.Value.FLUSH);
        check("AC 2D 9C 3S KD", "5S 4D KS AS 4C", 
                Hand.Value.STRAIGHT);
        check("KS AH 2H 3C 4H", "KC 2C TC 2D AS",
                Hand.Value.THREE_OF_A_KIND);
        check("AH 2C 9S AD 3C", "QH KS JS JD KD", 
                Hand.Value.TWO_PAIRS);
        check("6C 9C 8C 2D 7C", "2H TC 4C 9S AH", 
                Hand.Value.ONE_PAIR);
    }

    private void check(String handInput, String deckInput,
            Hand.Value expected) {
        Hand hand = new Hand(cardInputHelper(handInput));
        Card[] deck = cardInputHelper(deckInput);
        Game game = new Game(hand, deck);
        assertEquals(expected, game.getBestHand());
    }
}
