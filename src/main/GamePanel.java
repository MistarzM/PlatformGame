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
    private BufferedImage img;
    private BufferedImage[] idleAnimation;
    private int animationTick, animationIndex, animationRefresh = 30;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        importImg();
        LoadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void LoadAnimations() {
        idleAnimation = new BufferedImage[8];

        for(int i = 0; i < idleAnimation.length; i++){
            idleAnimation[i] = img.getSubimage((i%2) * 128, (i/2) * 64, 128, 64);
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/Idle.png");
        try {
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();             // close the input stream to avoid errors
            } catch (IOException e){
                e.printStackTrace();
            }
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

    private void UpdateAnimationTick() {
        animationTick++;
        if(animationTick >= animationRefresh){
            animationTick = 0;
            animationIndex++;
            if(animationIndex>= idleAnimation.length){
                animationIndex = 0;
            }
        }
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);

        UpdateAnimationTick();
        
        g.drawImage(idleAnimation[animationIndex], (int) moveRight, (int) moveDown, 256, 128, null);
    }

}
