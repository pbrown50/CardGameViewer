import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values)
    {
        cards = new ArrayList<Card>();
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suits[i], values[j]));
            }
        }
        cardsLeft = cards.size();
    }

    public boolean isEmpty() {
        if (cardsLeft == 0) {
            return true;
        }
        return false;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    public Card deal() {
        if (cardsLeft == 0) {
            return null;
        }
        cardsLeft--;
        return cards.remove(cardsLeft);
    }

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

