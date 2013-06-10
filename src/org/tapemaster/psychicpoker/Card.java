package org.tapemaster.psychicpoker;

import java.util.HashMap;
import java.util.Map;

/**
 * The Card class represents a game card.
 */
public class Card implements Comparable<Card> {

    /**
     * Rank of the card. Goes with card's caption:
     * 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K and A.
     */
    public enum Rank {
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("T"),
        JACK("J"),
        QUEEN("Q"),
        KING("K"),
        ACE("A");

        private static final Map<String, Rank> sDirectory;
        private final String mCaption;

        static {
            sDirectory = new HashMap<String, Rank>();
            for (Rank value : Rank.values()) {
                sDirectory.put(value.mCaption, value);
            }
        }

        /**
         * Gets Ranking object by caption.
         * 
         * @throws IllegalArgumentException
         *             if no such caption exists
         */
        public static Rank getByCaption(String caption) {
            final Rank result = sDirectory.get(caption);
            if (result == null) {
                throw new IllegalArgumentException("No such card ranking: " + caption);
            }
            return result;
        }

        /**
         * Creates new Ranking with caption.
         */
        Rank(String caption) {
            mCaption = caption;
        }

        @Override
        public String toString() {
            return mCaption;
        }
    }
    
    /**
     * Suit of the card.
     */
    public enum Suit {
        C, D, H, S;
    }

    private final Rank mValue;
    private final Suit mSuit;

    /**
     * Creates new Card object from input string.
     * 
     * @param input
     *            must contain two characters: first is ranking, second is suit
     */
    public Card(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Card caption must not be null");
        }
        if (input.length() != 2) {
            throw new IllegalArgumentException("Card caption must be string size of 2");
        }

        final String valuePart = input.substring(0, 1);
        final String suitPart = input.substring(1, 2);

        mValue = Rank.getByCaption(valuePart);
        mSuit = Suit.valueOf(suitPart);
        
        if (mValue == null) {
            throw new IllegalArgumentException("Couldn't find value '" + valuePart + "'");
        }
        if (mSuit == null) {
            throw new IllegalArgumentException("Couldn't find suit '" + suitPart + "'");
        }
    }

    @Override
    /**
     * Compares two cards basing on the ranking only. It doesn't depend on the cards' suit.
     */
    public int compareTo(Card that) {
        return mValue.ordinal() - that.mValue.ordinal();
    }

    @Override
    public String toString() {
        return mValue.toString() + mSuit.toString();
    }

    /**
     * Gets card's value.
     */
    public Rank getValue() {
        return mValue;
    }

    /**
     * Gets card's suit.
     */
    public Suit getSuit() {
        return mSuit;
    }

    /**
     * Checks if this card's ranking is in sequence with other card.
     * 
     * @param other
     *            card to compare with
     * @return true if cards are in increasing sequence
     */
    public boolean isNextOf(Card other) {
        return (mValue.ordinal() - other.mValue.ordinal() == 1);
    }
}
