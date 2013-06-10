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
     * prints value of the best possible hand.
     * The lines must contain card captions for the hand and the deck.
     */
	public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java PsychicPoker source_file");
            System.exit(1);
        }

        final File inputFile = new File(args[0]);
        new PsychicPoker().processInput(inputFile);
    }

    private void processInput(File inputFile) {
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
        final String handInput = line.substring(0, 14).trim();
        final String deckInput = line.substring(15).trim();
        Hand hand = new Hand(cardInputHelper(handInput));
        Card[] deck = cardInputHelper(deckInput);
        Game game = new Game(hand, deck);

        Value best = game.getBestHand();
        System.out.println("Hand: " + hand.toString() + " Deck: "
                + getDeckString(deck) + " Best hand: " + best);
    }

    private String getDeckString(Card[] deck) {
        StringBuilder builder = new StringBuilder();
        for (Card card : deck) {
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
    public static Card[] cardInputHelper(String input) {
        if (input.length() != Hand.NUMBER_OF_CARDS * 3 - 1) {
            throw new IllegalArgumentException(
                    "Input string has wrong format, expected five cards "
                            + "two symbols each separated with space.");
        }
        final Card[] cards = new Card[Hand.NUMBER_OF_CARDS];
        for (int i = 0; i < Hand.NUMBER_OF_CARDS; i++) {
            final int pos = i * 3;
            cards[i] = new Card(input.substring(pos, pos + 2));
        }
        return cards;
    }
}
