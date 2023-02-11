import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    // Declares instance variables
    private GameViewer window;
    private ArrayList<Player> players;
    private Deck cards;
    private Player user;
    private Player dealer;
    private boolean stay = false;
    private boolean isbusted = false;
    private boolean first = false;
    // Final variables
    private final int WINDOW_HEIGHT = 800;
    private final int WINDOW_LENGTH = 1000;
    // Instructor to initialize instance variables
    public Game() {
        // New players
        players = new ArrayList<Player>();
        user = new Player("User");
        dealer = new Player("Dealer");
        players.add(user);
        players.add(dealer);
        // Creates deck of cards
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "8", "10", "J", "Q", "K"};
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        cards = new Deck(ranks, suits, values);
        cards.shuffle();
        // Initializes window
        window = new GameViewer(WINDOW_LENGTH, WINDOW_HEIGHT, this);
    }
    // Calls and runs game
    public static void main(String[] args) {
        Game a = new Game();
        a.playGame();
    }
    // Calls methods necessary to run a game
    public void playGame() {
        // Paints window
        window.repaint();
        // Makes sure dealer and user hand is empty
        clearHand();
        // Displays instructions
        printInstructions();
        // Deals cards to user and dealer
        dealCards();
        // Displays cards
        printCards();
        // Does the following while the game is not over
        while(!isOver()) {
            // Commences a round
            round();
            // Prints cards if user busted
            if (!stay || isbusted) {
                printCards();
            }
        }
        // Commences a round for the dealer
        dealerRound();
        // Displays cards
        printCards();
        // Displays winner
        printWinner();
        // Paints window
        window.repaint();
        // If user agrees to continue playing, do the following
        if (newRound()) {
            // Empty user and dealer hands
            clearHand();
            // Play a new game
            playGame();
        }
    }
    // Checks if game is over
    public boolean isOver() {
        // Returns true if the user has selected the move stay
        if (stay == true) {
            dealerRound();
            return true;
        }
        // Checks to see if the user busted or got 21
        else if (getTotal(user) >= 21) {
            dealerRound();
            if (getTotal(user) > 21) { isbusted = true; }
            return true;
        }
        // Round is not over
        else {
            return false;
        }
    }
    // Commences a round for the dealer
    public void dealerRound() {
        // While the total of the dealers points is less than 17, continue to hit
        while (getTotal(dealer) < 17) {
            dealer.addCard(cards.deal());
        }
        // Display message only once to the user
        if (!first) {
            System.out.println("Dealers turn!");
        }
        first = true;
        // Turns back card over so that it is now facing up
        dealer.getHand().get(1).setIsBack(false);
    }
    // Deals cards to user and dealer
    public void dealCards() {
        dealer.addCard(cards.deal());
        dealer.addCard(cards.deal());
        user.addCard(cards.deal());
        user.addCard(cards.deal());
    }
    // Commences a round for user
    public void round() {
        // Prompts user for move
        Scanner s = new Scanner(System.in);
        System.out.println("Press 'h' to hit or 's' to stay.");
        String a = s.nextLine();
        // Adds card if hit
        if (a.equals("h")) {
            user.addCard(cards.deal());
        }
        // Stay turned to true if stay is inputted
        else if (a.equals("s")) {
            stay = true;
        }
    }
    // Prints winner in different casenarios
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
    // Prints user and dealer cards
    public void printCards() {
        // If user turn is over, dealer cards can be turned over and all can be shown
        if (first) {
            System.out.println("---------------------------\nDealer Cards:\n" + dealer.getHand());

        }
        // If user turn is not finished, dealer card must remained turned over, only showing one card
        else {
            System.out.println("---------------------------\nDealer Cards:\n[ " + dealer.getHand().get(0) + " , *** ]");
            // Turns card over
            dealer.getHand().get(1).setIsBack(true);
        }
        // Prints user cards
        System.out.println("\nYour Cards:\n" + user.getHand() + "\n\n---------------------------");
        // Paints window with updated cards
        window.repaint();
    }
    // Empty's hand of user and dealer and resets variables to original
    public void clearHand() {
        user.getHand().clear();
        dealer.getHand().clear();
        stay = false;
        first = false;
    }
    // Prints instructions
    public void printInstructions() {
        System.out.println("< B L A C K     J A C K >\nInstructions: https://en.wikipedia.org/wiki/Blackjack\n");
    }
    // Returns total points of a hand
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
                // Prompts user on how they would like to use their Ace
                do {
                    System.out.println("Would you like your " + hand.getHand().get(i) + " to count as '1' or '11' points?");
                    a = s.nextLine();
                } while (a.equals("1") && a.equals("11")) ;
                // Adds points for Ace based on user input
                if (a.equals("1")) {
                    total += 1;
                }
                else if (a.equals("11")) {
                    total += 11;
                }
            }
            else {
                // Adds up total points of cards
                total += hand.getHand().get(i).getPoint();
            }
        }
        return total;
    }
    // Prompts user to ask if they would like to start a new round
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
    // Getter and setter methods
    public Deck getCards() {
        return cards;
    }
    public Player getDealer() {
        return dealer;
    }
    public Player getUser() {
        return user;
    }
}