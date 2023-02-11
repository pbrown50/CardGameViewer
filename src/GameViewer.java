import javax.swing.*;
import java.awt.*;
public class GameViewer extends JFrame {
    // Declares instance variables
    private Game game;
    // Instructor to initialize instance variables
    public GameViewer(int width, int height, Game g) {
        this.game = g;
        // Opens window
        this.setTitle("BlackJack");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
    }
    // Paints window when called
    public void paint(Graphics g) {
        // Displays background image provided
        Image a = new ImageIcon("Resources/Background.png").getImage();
        g.drawImage(a, 0, 0,1000, 800, this);

        int dx = 100;
        int dy = 250;
        int x = 100;
        int y = 550;
        // Displays dealers hand
        for (Card c : game.getDealer().getHand()) {
            c.drawImage(dx, dy, g, this);
            dx += 200;
        }
        // Displays users hand
        for (Card c : game.getUser().getHand()) {
            c.drawImage(x, y, g, this);
            x += 200;
        }
        // Displays message if the game is over
        if (game.isOver()) {
            g.setColor(Color.BLACK);
            String str = "Game Over! Enter 'n' to play again or 'e' to exit.";
            Font f = new Font("Arial", Font.BOLD, 24);
            g.setFont(f);
            g.drawString(str,120, 150);
        }
    }
    // Paints over window with new background to erase
    public void clear (Graphics g) {
        Image a = new ImageIcon("Resources/Background.png").getImage();
        g.drawImage(a, 0, 0, this);
    }
    // Closes program as needed
    public void setDefaultCloseOperation(int exitOnClose) {
    }
}