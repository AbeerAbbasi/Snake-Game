import javax.swing.*;
/**
 * This class represents the main game window, containing the game's visual elements.
 * It creates a frame for the Snake Game, sets up the game panel, and ensures that the window is properly initialized.
 * @author Abeer Abbasi
 * @version 1.0
 */

public class Frame extends JFrame {
    Panel panel;

    public Frame() {
        panel = new Panel();
        this.add(panel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
