package org.tapemaster.psychicpoker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.tapemaster.psychicpoker.Hand.Value;

/**
 * Main class of PsychicPoker.
 */
public class PsychicPoker {

    /**
     * Reads input file given as a first element in arguments and for each line
     * prints value of the best possible hand. The lines must contain card
     * captions for the hand and the deck.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java PsychicPoker source_file");
            System.exit(1);
        }

        final File inputFile = new File(args[0]);
        new PsychicPoker().readInputAndPlay(inputFile);
    }

    private void readInputAndPlay(File inputFile) {
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    playGame(line);
                } catch (Exception e) {
                    System.err.println("Error while processing line '" + line + "': " + e);
                }
            }
        } catch (IOException ioe) {
            System.err.println("Error while reading from file: " + ioe);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing input file: " + e);
            }
        }
    }

    private void playGame(String line) {
        final int handLength = (Card.CAPTION_LENGTH + 1) * Hand.NUMBER_OF_CARDS;
        final String handInput = line.substring(0, handLength).trim();
        final String deckInput = line.substring(handLength).trim();

        final Hand hand = new Hand(parseCards(handInput));
        final Card[] deck = parseCards(deckInput);
        final Game game = new Game(hand, deck);

        final Value best = game.getBestHand();
        System.out.println("Hand: " + hand.toString() + " Deck: "
                + cardsArrayToString(deck) + " Best hand: " + best);
    }

    private String cardsArrayToString(Card[] cards) {
        final StringBuilder builder = new StringBuilder();
        for (Card card : cards) {
            builder.append(card);
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    /**
     * Creates array of Cards from input string.
     * 
     * @param input
     *            String consisting of {@link Card} captions separated with spaces
     *            
     * @throws IllegalArgumentException if input string is in wrong format           
     */
    public static Card[] parseCards(String input) {
        final int oneCardLength = Card.CAPTION_LENGTH + 1;
        if (input.length() != Hand.NUMBER_OF_CARDS * oneCardLength - 1) {
            throw new IllegalArgumentException(
                    "Input string has wrong format, expected five cards "
                            + "two symbols each separated with space.");
        }
        final Card[] cards = new Card[Hand.NUMBER_OF_CARDS];
        for (int i = 0; i < Hand.NUMBER_OF_CARDS; i++) {
            final int pos = i * oneCardLength;
            cards[i] = new Card(input.substring(pos, pos + Card.CAPTION_LENGTH));
        }
        return cards;
    }
}
