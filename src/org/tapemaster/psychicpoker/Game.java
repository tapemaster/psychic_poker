package org.tapemaster.psychicpoker;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Card[] mDeck;
    private final Hand mHand;

    public Game(Hand hand, Card[] deck) {
        mHand = hand;
        mDeck = deck;
    }

    public List<Hand> getAllPossibleHands() {
        final List<Hand> result = new ArrayList<Hand>();
        result.add(mHand);
        for (int depth = 1; depth <= Hand.NUMBER_OF_CARDS; depth++) {
            for (List<Integer> discard : getVariation(new ArrayList<Integer>(), 0, depth)) {
                addVariation(result, discard.toArray(new Integer[] {}));
            }
        }
        return result;
    }

    private void addVariation(List<Hand> result, Integer[] toDiscard) {
        final int[] discard = new int[toDiscard.length];
        for (int i = 0; i < discard.length; i++) {
            discard[i] = toDiscard[i];
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
