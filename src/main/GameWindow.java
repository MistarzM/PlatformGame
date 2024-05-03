package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame{     // JFrame -> frame

    public GameWindow(GamePanel gamePanel){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Terminates the process when the window is closed

        this.add(gamePanel);
        this.setLocationRelativeTo(null);       // spawn the window in the center of our screen
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
