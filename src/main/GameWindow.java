package main;

import javax.swing.JFrame;

public class GameWindow extends JFrame{     // JFrame -> frame

    public GameWindow(GamePanel gamePanel){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Terminates the process when the window is closed

        this.add(gamePanel);
        this.setLocationRelativeTo(null);       // spawn the window in the center of our screen
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

    }
}
