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
        final List<Hand> result = new ArrayList<Hand>();
        result.add(mHand);
        for (int depth = 1; depth <= Hand.NUMBER_OF_CARDS; depth++) {
            for (List<Integer> discard : getVariation(new ArrayList<Integer>(), 0, depth)) {
                addVariation(result, discard);
            }
        }

        return Collections.max(result).getValue();
    }

    private void addVariation(List<Hand> result, List<Integer> toDiscard) {
        final int[] discard = new int[toDiscard.size()];
        for (int i = 0; i < discard.length; i++) {
            discard[i] = toDiscard.get(i);
        }
        final Hand otherHand = mHand.discard(discard, mDeck);
        result.add(otherHand);
    }

    private List<List<Integer>> getVariation(List<Integer> base, int start,
            int maxDepth) {
        if (maxDepth == 1) {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            for (int i = start; i < Hand.NUMBER_OF_CARDS; i++) {
                List<Integer> thisOne = new ArrayList<Integer>(base);
                thisOne.add(i);
                result.add(thisOne);
            }
            return result;
        } else {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            for (int i = start; i < Hand.NUMBER_OF_CARDS; i++) {
                List<Integer> newBase = new ArrayList<Integer>(base);
                newBase.add(i);
                List<List<Integer>> thisOne = getVariation(newBase, i + 1,
                        maxDepth - 1);
                result.addAll(thisOne);
            }
            return result;
        }
    }
}
