package org.tapemaster.psychicpoker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Hand class.
 */
public class HandTest {

    @Test
    public void testHandCompare() {
        assertTrue(hand("TH JH QC QD QS").compareTo(hand("QH KH AH 2S 6S")) > 0);
        assertTrue(hand("TH JH QC 2D QS").compareTo(hand("QH KH AH KS KD")) < 0);
        assertTrue(hand("TH JH QC 2D QS").compareTo(hand("QH KH AH QD KD")) < 0);
        assertTrue(hand("TH JH QC 2D QS").compareTo(hand("QH 2H AH QD KD")) == 0);
    }

    private Hand hand(String input) {
        return new Hand(PsychicPoker.parseCards(input));
    }
}
