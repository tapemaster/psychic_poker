package org.tapemaster.psychicpoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tapemaster.psychicpoker.Card.Rank;

public class Hand implements Comparable<Hand> {

    public static final int NUMBER_OF_CARDS = 5;

    public enum Value {
        HIGHEST_CARD("highest-card"),
        ONE_PAIR("one-pair"),
        TWO_PAIRS("two-pairs"),
        THREE_OF_A_KIND("three-of-a-kind"),
        STRAIGHT("straight"),
        FLUSH("flush"),
        FULL_HOUSE("full-house"),
        FOUR_OF_A_KIND("four-of-a-kind"),
        STRAIGHT_FLUSH("straight-flush");
        
        private final String mCaption;

        Value(String caption) {
            mCaption = caption;
        }

        @Override
        public String toString() {
            return mCaption;
        }
    }
    
    private final Card[] mCards;

    public Hand(Card[] cards) {
        if (cards.length != NUMBER_OF_CARDS) {
            throw new IllegalArgumentException("Number of cards in hand must be " + NUMBER_OF_CARDS);
        }
        mCards = cards;
        Arrays.sort(mCards);
    }

    public Value getValue() {
        if (isFlush() && isStraight()) {
            return Value.STRAIGHT_FLUSH;
        }

        final List<Rank> pairs = findPairs();
        final Rank three = findThreeOfaKind();

        if (findFourOfaKind() != null) {
            return Value.FOUR_OF_A_KIND;
        }
        if ((three != null) && (pairs.size() == 1)) {
            return Value.FULL_HOUSE;
        }
        if (isFlush()) {
            return Value.FLUSH;
        }
        if (isStraight()) {
            return Value.STRAIGHT;
        }
        if (three != null) {
            return Value.THREE_OF_A_KIND;
        }
        if (pairs.size() == 2) {
            return Value.TWO_PAIRS;
        }
        if (pairs.size() != 0) {
            return Value.ONE_PAIR;
        }

        return Value.HIGHEST_CARD;
    }

    private Rank findFourOfaKind() {
        return findNOfaKind(4);
    }

    private Rank findThreeOfaKind() {
        return findNOfaKind(3);
    }

    private Rank findNOfaKind(int n) {
        Rank middle = mCards[2].getValue();

        int foundCardsLikeMiddle = 0;
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            if (mCards[i].getValue() == middle) {
                foundCardsLikeMiddle++;
            }
        }

        if (foundCardsLikeMiddle == n) {
            return middle;
        } else {
            return null;
        }
    }

    private List<Rank> findPairs() {
        List<Rank> result = new ArrayList<Rank>();
        for (int i = 1; i < NUMBER_OF_CARDS - 1; i++) {
            final Rank current = mCards[i].getValue();
            final Rank prev = mCards[i - 1].getValue();
            final Rank next = mCards[i + 1].getValue();
            if (current == prev) {
                if (current != next) { // check for three of a kind
                    result.add(current);
                }
                i++;
            }
        }

        final Rank current = mCards[NUMBER_OF_CARDS - 2].getValue();
        final Rank prev = mCards[NUMBER_OF_CARDS - 3].getValue();
        final Rank next = mCards[NUMBER_OF_CARDS - 1].getValue();
        if ((current == next) && (prev != current)) {
            result.add(current);
        }
        return result;
    }

    private boolean isFlush() {
        for (int i = 1; i < NUMBER_OF_CARDS; i++) {
            if (mCards[i].getSuit() != mCards[i - 1].getSuit()) {
                return false;
            }
        }
        return true;
    }

    private boolean isStraight() {
        // check first four cards
        for (int i = 1; i < NUMBER_OF_CARDS - 1; i++) {
            if (mCards[i].isNextOf(mCards[i - 1]) == false) {
                return false;
            }
        }
        
        final Card last = mCards[NUMBER_OF_CARDS - 1];
        final Card lastButOne = mCards[NUMBER_OF_CARDS - 2];
        final Card first = mCards[0];

        if (last.isNextOf(lastButOne)) {
            return true; //normal straight
        }

        if ((last.getValue() == Rank.ACE) && (first.getValue() == Rank.TWO)) {
            return true; // a wheel
        }

        return false;
    }

    public Hand discard(int[] toDiscard, Card[] deck) {
        final Card[] newCards = Arrays.copyOf(mCards, NUMBER_OF_CARDS);

        int deckIndex = 0;
        for (int discardIndex : toDiscard) {
            newCards[discardIndex] = deck[deckIndex];
            deckIndex++;
        }

        return new Hand(newCards);
    }

    @Override
    public int compareTo(Hand other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Card card : mCards) {
            result.append(card);
            result.append(" ");
        }

        return result.toString().trim();
    }

}
