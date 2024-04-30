package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {     // JPanel -> picture

    public GamePanel(){

    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);
        g.fillRect(10, 10, 40, 50);
    }
}
