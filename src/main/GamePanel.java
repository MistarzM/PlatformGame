package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {     // JPanel -> picture

    private MouseInputs mouseInputs;
    private float moveRight = 0, moveDown = 0;
    private BufferedImage img, subImg;
    public boolean attack = false;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        importImg();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/Attacks.png");
        try {
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
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

        subImg = img.getSubimage(3*128, 0*64, 128, 64);
        if(attack) {
            g.drawImage(subImg, (int) moveRight, (int) moveDown, 256, 128, null);
        } else {
            g.drawImage(img.getSubimage(0, 0, 128, 64), (int) moveRight, (int) moveDown, 256, 128, null);
        }
    }
}
