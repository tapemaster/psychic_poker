package org.tapemaster.psychicpoker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit test for Card class.
 */
public class CardTest {

    @Test
    public void testCardCreate() {
        final Card card = new Card("AH");
        assertEquals("AH", card.toString());

        expectIllegalArgumentException(null);
        expectIllegalArgumentException("");
        expectIllegalArgumentException("A");
        expectIllegalArgumentException("AHA");
        expectIllegalArgumentException("BH");
        expectIllegalArgumentException("AA");
        expectIllegalArgumentException("Q ");
        expectIllegalArgumentException(" H");
    }
    
    private void expectIllegalArgumentException(String caption) {
        try {
            new Card(caption);
            fail("Exception expected while creating Card with caption" + caption);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testCardsCompare() {
        checkCardsEqual("AH", "AH");
        checkCardsEqual("AH", "AS");
        checkCardsEqual("3S", "3D");
        
        checkCardHigher("3H", "2H");
        checkCardHigher("AH", "6S");
        checkCardHigher("JD", "TC");

        checkCardLower("3S", "5C");
        checkCardLower("KD", "AD");
        checkCardLower("4S", "QH");
    }

    private void checkCardsEqual(String a, String b) {
        final int result = new Card(a).compareTo(new Card(b));
        assertEquals(0, result);
    }

    private void checkCardHigher(String a, String b) {
        final int result = new Card(a).compareTo(new Card(b));
        assertTrue(result > 0);
    }

    private void checkCardLower(String a, String b) {
        final int result = new Card(a).compareTo(new Card(b));
        assertTrue(result < 0);
    }

}
