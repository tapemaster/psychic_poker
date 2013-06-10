package org.tapemaster.psychicpoker;

import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card> {

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

        public static Rank getByCaption(String caption) {
            final Rank result = sDirectory.get(caption);
            if (result == null) {
                throw new IllegalArgumentException("No such card ranking: " + caption);
            }
            return result;
        }

        Rank(String caption) {
            mCaption = caption;
        }

        @Override
        public String toString() {
            return mCaption;
        }
    }
    
    public enum Suit {
        C, D, H, S;
    }

    private final Rank mValue;
    private final Suit mSuit;

    public Card(String caption) {
        if (caption == null) {
            throw new IllegalArgumentException("Card caption must not be null");
        }
        if (caption.length() != 2) {
            throw new IllegalArgumentException("Card caption must be string size of 2");
        }

        final String valuePart = caption.substring(0, 1);
        final String suitPart = caption.substring(1, 2);

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
    public int compareTo(Card that) {
        return mValue.ordinal() - that.mValue.ordinal();
    }

    @Override
    public String toString() {
        return mValue.toString() + mSuit.toString();
    }

    public Rank getValue() {
        return mValue;
    }

    public Suit getSuit() {
        return mSuit;
    }

    public boolean isNextOf(Card other) {
        return (mValue.ordinal() - other.mValue.ordinal() == 1);
    }
}
