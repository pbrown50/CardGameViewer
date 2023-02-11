import javax.swing.*;
import java.awt.*;
public class Card {
    // Declares instance variables
    private String rank;
    private String suit;
    private int point;
    private Image image;
    private Image prev;
    private boolean isBack = false;
    // Instructor to initialize instance variables
    public Card(String rank, String suit, int point, Image image) {
        this.rank = rank;
        this.suit = suit;
        this.point = point;
        this.image = image;
    }
    // Getter and setter methods for instance variables
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getSuit() {
        return suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public void setIsBack(boolean back) {
        isBack = back;
    }
    // Draws corresponding image of card when called
    public void drawImage(int x, int y, Graphics g, GameViewer game) {
        // Changes image being used if the card is supposed to appear turned over
        if (isBack) {
            prev = new ImageIcon("Resources/back.png").getImage();
            g.drawImage(prev, x, y, 100, 200, game);
        }
        // Prints normal card photo
        else {
            g.drawImage(image, x, y, 100, 200, game);
        }
    }
    public String toString() {
        return rank + " of " + suit;
    }
}
