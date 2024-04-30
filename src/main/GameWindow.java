package main;

import javax.swing.JFrame;
import java.awt.*;

public class GameWindow extends JFrame{     // JFrame -> frame

    public GameWindow(GamePanel gamePanel){
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Terminates the process when the window is closed

        this.add(gamePanel);
        this.setVisible(true);

    }
}
