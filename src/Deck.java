import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class Deck {
    // Declares instance variables
    private ArrayList<Card> cards;
    private int cardsLeft;
    // Instructor to initialize instance variables
    public Deck(String[] ranks, String[] suits, int[] values)
    {
        cards = new ArrayList<Card>();
        int counter = 1;
        // Initializes images for each card by attaching their corresponding png files
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                Image image = new ImageIcon("Resources/" + counter + ".png").getImage();
                cards.add(new Card(ranks[i], suits[j], values[i], image));
                counter++;
            }
        }
        cardsLeft = cards.size();
    }
    // Returns if deck is empty
    public boolean isEmpty() {
        if (cardsLeft == 0) {
            return true;
        }
        return false;
    }
    // Returns how many cards are left in deck
    public int getCardsLeft()
    {
        return cardsLeft;
    }
    // Deals a card from deck to a hand
    public Card deal() {
        if (cardsLeft == 0) {
            return null;
        }
        cardsLeft--;
        return cards.remove(cardsLeft);
    }
    // Shuffles deck to randomize order of cards
    public void shuffle() {
        for (int i = cards.size() - 1; i >= 0; i--) {
            int r = (int) (Math.random() * i);
            Card placeholder = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, placeholder);
        }
        cardsLeft = cards.size();
    }
}

