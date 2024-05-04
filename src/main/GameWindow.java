package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.Point;

import static main.Game.PANEL_WIDTH;
import static main.Game.PANEL_HEIGHT;

public class GameWindow extends JFrame{     // JFrame -> frame

    public GameWindow(GamePanel gamePanel){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Terminates the process when the window is closed

        this.add(gamePanel);

        this.setLocationRelativeTo(null);       // spawn the window in the center of our screen
        Point currentLocation = this.getLocation();
        int newX = currentLocation.x - PANEL_WIDTH/2;
        int newY = currentLocation.y - PANEL_HEIGHT/2;
        this.setLocation(newX, newY);


        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}
