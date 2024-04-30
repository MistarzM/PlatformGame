package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {     // JPanel -> picture

    private MouseInputs mouseInputs;
    private int moveRight = 0, moveDown = 0;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void setMoveRight(int right) {
        this.moveRight = right;
        repaint();
    }
    public void setMoveDown(int down){
        this.moveDown = down;
        repaint();
    }
    public int getMoveRight(){
        return moveRight;
    }
    public int getMoveDown(){
        return moveDown;
    }
    public void setRectPosition(int x, int y){
        this.moveDown = y;
        this.moveRight = x;
        repaint();
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);
        g.fillRect(moveRight, moveDown, 40, 50);
    }
}
