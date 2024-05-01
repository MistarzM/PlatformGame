package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {     // JPanel -> picture

    private MouseInputs mouseInputs;
    private int moveRight = 0, moveDown = 0;
    private int motionX = 1, motionY = 1;
    private int frames = 0;
    private long previousCheck = 0;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void setMoveRight(int right) {
        this.moveRight = right;
    }
    public void setMoveDown(int down){
        this.moveDown = down;
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
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);

        moveRect();
        g.fillRect(moveRight, moveDown, 40, 50);

        frames++;
        long currentTime = System.nanoTime();
        if(currentTime - previousCheck >= 1_000_000_000 ){
            previousCheck = currentTime;
            System.out.println(frames + " FPS");
            frames = 0;
        }

        repaint();
    }

    private void moveRect() {
        moveRight += motionX;
        if(moveRight >500 || moveRight < 0){
            motionX *= -1;
        }
        moveDown +=motionY;
        if(moveDown > 500 || moveDown < 0){
            motionY *= -1;
        }
    }
}
