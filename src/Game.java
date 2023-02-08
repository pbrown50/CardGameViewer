import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private Deck cards;
    private Player user;
    private Player dealer;
    private boolean stay = false;
    private boolean isbusted = false;
    private boolean first = false;
    public Game() {
        players = new ArrayList<Player>();
        user = new Player("User");
        dealer = new Player("Dealer");
        players.add(user);
        players.add(dealer);
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "8", "10", "J", "Q", "K"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        cards = new Deck(ranks, suits, values);
        cards.shuffle();
    }

    public static void main(String[] args) {
        Game a = new Game();
        a.playGame();
    }

    public void playGame() {
        clearHand();
        printInstructions();
        dealCards();
        printCards();
        while(!isOver()) {
            round();
            if (!stay || isbusted) {
                printCards();
            }
        }
        dealerRound();
        printCards();
        printWinner();
        if (newRound()) {
            clearHand();
            playGame();
        }
    }

    public boolean isOver() {
        if (stay == true) {
            dealerRound();
            //checkWin and print winner
            return true;
        }
        else if (getTotal(user) >= 21) {
            //check if dealer also busted and print tie or lost
            dealerRound();
            if (getTotal(user) > 21) { isbusted = true; }
            return true;
        }
        else {
            //round is not over
            return false;
        }
    }

    public void dealerRound() {
        while (getTotal(dealer) < 17) {
            dealer.addCard(cards.deal());
        }
        if (!first) {
            System.out.println("Dealers turn!");
        }
        first = true;
    }

    public void dealCards() {
        dealer.addCard(cards.deal());
        dealer.addCard(cards.deal());
        user.addCard(cards.deal());
        user.addCard(cards.deal());
    }

    public void round() {
        Scanner s = new Scanner(System.in);
        System.out.println("Press 'h' to hit or 's' to stay.");
        String a = s.nextLine();
        if (a.equals("h")) {
            user.addCard(cards.deal());
        }
        else if (a.equals("s")) {
            stay = true;
        }
    }

    public void printWinner() {
        dealerRound();
        if (isbusted) {
            if (getTotal(dealer) > 21) {
                System.out.println("You and the dealer both busted! Draw!");
            }
            else {
                System.out.println("You Busted! Dealer wins!");
            }
        }
        else if (getTotal(user) == 21) {
            if (getTotal(dealer) == 21) {
                System.out.println("You and the dealer both hit 21! Draw!");
            }
            else {
                System.out.println("Blackjack! You hit 21!");
            }
        }
        else if (!isbusted) {
            if (getTotal(user) > getTotal(dealer) && getTotal(dealer) < 21) {
                System.out.println("You win!");
            }
            else if (getTotal(dealer) > 21) {
                System.out.println("You win!");
            }
            else if (getTotal(user) == getTotal(dealer)) {
                System.out.println("Draw!");
            }
            else {
                System.out.println("Dealer wins!");
            }
        }
    }
    public void printCards() {
        if (first) {
            System.out.println("---------------------------\nDealer Cards:\n" + dealer.getHand());
        }
        else {
            System.out.println("---------------------------\nDealer Cards:\n[ " + dealer.getHand().get(0) + " , *** ]");
        }
        System.out.println("\nYour Cards:\n" + user.getHand() + "\n\n---------------------------");
    }
    public void clearHand() {
        user.getHand().clear();
        dealer.getHand().clear();
        stay = false;
        first = false;
    }
    public void printInstructions() {
        System.out.println("< B L A C K     J A C K >\nInstructions: https://en.wikipedia.org/wiki/Blackjack\n");
    }

    public int getTotal(Player hand) {
        int total = 0;
        for (int i = 0; i < hand.getHand().size(); i++) {
            total += hand.getHand().get(i).getPoint();
        }
        if (total >= 21 || hand.getName().equals("Dealer") || stay) {
            return total;
        }
        else {
            total = 0;
        }
        for (int i = 0; i < hand.getHand().size(); i++) {
            if (hand.getHand().get(i).getPoint() == 1) {
                Scanner s = new Scanner(System.in);
                String a;
                do {
                    System.out.println("Would you like your " + hand.getHand().get(i) + " to count as '1' or '11' points?");
                    a = s.nextLine();
                } while (a.equals("1") && a.equals("11")) ;
                if (a.equals("1")) {
                    total += 1;
                }
                else if (a.equals("11")) {
                    total += 11;
                }
            }
            else {
                total += hand.getHand().get(i).getPoint();
            }
        }
        return total;
    }
    public boolean newRound() {
        Scanner s = new Scanner(System.in);
        System.out.println("\nPress 'n' to start a new round. Press 'e' to end game");
        String a = s.nextLine();
        if (a.equals("n")) {
            return true;
        } else if (a.equals("e")) {
            return false;
        } else {
            return false;
        }
    }
}