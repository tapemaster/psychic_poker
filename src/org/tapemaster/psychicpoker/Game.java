package org.tapemaster.psychicpoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tapemaster.psychicpoker.Hand.Value;

/**
 * The Game class represents single game of draw poker.
 */
public class Game {

    private final Card[] mDeck;
    private final Hand mHand;

    /**
     * All possible discard variations.
     */
    private static final List<int[]> sVariations = new ArrayList<int[]>();

    static {
        getVariations(new ArrayList<Integer>(), 0);
    }
    
    /**
     * This function is called recursively, and on each iteration it adds new
     * discard variation.
     * 
     * @param base
     *            the base of the discard list to add new element
     * @param start
     *            the index to start from to skip duplicate variations
     */
    private static void getVariations(List<Integer> base, int start) {
        for (int i = start; i < Hand.NUMBER_OF_CARDS; i++) {
            List<Integer> newVariation = new ArrayList<Integer>(base);
            newVariation.add(i);
            sVariations.add(integerListToIntArray(newVariation));
            getVariations(newVariation, i + 1);
        }
    }

    /**
     * Converts List<Integer> to int[], for convenience.
     */
    private static int[] integerListToIntArray(List<Integer> list) {
        final int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Creates new Game object from Hand and deck.
     */
    public Game(Hand hand, Card[] deck) {
        mHand = hand;
        mDeck = deck;
    }

    /**
     * Gets value of the best possible hand.
     */
    public Value getBestHand() {
        final List<Hand> possibleHands = new ArrayList<Hand>();
        possibleHands.add(mHand); //starting from hand without any cards changed

        for (int[] variation : sVariations) {
            final Hand otherHand = mHand.discard(variation, mDeck);
            possibleHands.add(otherHand);
        }

        return Collections.max(possibleHands).getValue();
    }
}
