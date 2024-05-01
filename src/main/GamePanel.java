package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;
import java.awt.*;

import java.util.Random;

public class GamePanel extends JPanel {     // JPanel -> picture

    private MouseInputs mouseInputs;
    private float moveRight = 0, moveDown = 0;
    private float motionX = 0.5f, motionY = 0.3f;
    private Color rectColor = new Color(100, 150, 20);
    private Random random;

    public GamePanel(){
        random = new Random();
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
    public float getMoveRight(){
        return moveRight;
    }
    public float getMoveDown(){
        return moveDown;
    }
    public void setRectPosition(int x, int y){
        this.moveDown = y;
        this.moveRight = x;
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);

        moveRect();
        g.setColor(rectColor);
        g.fillRect((int)(moveRight), (int) (moveDown), 60, 40);

    }

    private void moveRect() {
        moveRight += motionX;
        if(moveRight >500 || moveRight < 0){
            motionX *= -1;
            rectColor = getRandomColor();
        }
        moveDown +=motionY;
        if(moveDown > 500 || moveDown < 0){
            motionY *= -1;
            rectColor = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(0, 255);
        int g = random.nextInt(0, 255);
        int b = random.nextInt(0, 255);

        return new Color(r, g, b);
    }
}
