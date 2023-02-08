import java.util.ArrayList;
public class Player {
    private String name;
    private ArrayList<Card> cards;
    private ArrayList<Card> hand;
    public Player(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
        hand = new ArrayList<Card>();
    }
    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
        cards = new ArrayList<Card>();
    }
    public String getName() {
        return name;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public ArrayList<Card> getHand() { return hand; }
    public void addCard(Card card) { hand.add(card); }
    public String toString() {
        return name + "'s cards: " + hand;
    }
}
