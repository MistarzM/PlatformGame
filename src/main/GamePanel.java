package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utils.Constants.PlayerConstants.*;    // import all actions: IDLE, ATTACK etc.

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
    private BufferedImage   idleImg, crouchIdleImg, runImg, jumpImg, healthImg,
                            hurtImg, deathImg, climbImg, hangingImg, slideImg,
                            rollImg, prayImg, attackImg, airAttackImg, crouchAttackImg;
    private BufferedImage[] idleAnimation, crouchIdleAnimation, runAnimation, jumpAnimation, healthAnimation,
                            hurtAnimation, deathAnimation, climbAnimation, hangingAnimation, slideAnimation,
                            rollAnimation, prayAnimation, attackAnimation, airAttackAnimation, crouchAttackAnimation;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;

    public boolean isRunning = false;


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
        crouchIdleAnimation = new BufferedImage[8];
        runAnimation = new BufferedImage[8];
        jumpAnimation = new BufferedImage[8];
        healthAnimation = new BufferedImage[8];
        hurtAnimation = new BufferedImage[3];
        deathAnimation = new BufferedImage[4];
        climbAnimation = new BufferedImage[6];
        hangingAnimation = new BufferedImage[8];
        slideAnimation = new BufferedImage[10];
        rollAnimation = new BufferedImage[4];
        prayAnimation = new BufferedImage[12];
        attackAnimation = new BufferedImage[40];
        airAttackAnimation = new BufferedImage[7];
        crouchAttackAnimation = new BufferedImage[7];

        for(int i = 0; i < idleAnimation.length; i++){
            idleAnimation[i] = idleImg.getSubimage((i%2) * 128, (i/2) * 64, 128, 64);
            runAnimation[i] = runImg.getSubimage((i%2) * 128, (i/2) * 64, 128, 64);
        }
        for(int i = 0; i < attackAnimation.length; i++){
            attackAnimation[i] = attackImg.getSubimage((i%8) * 128, (i/8) * 64, 128, 64);
        }
    }

    private void importImg() {
        InputStream isIdle = getClass().getResourceAsStream("/knight/Idle.png");
        try {
            idleImg = ImageIO.read(isIdle);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                isIdle.close();             // close the input stream to avoid errors
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isCrouchIdle = getClass().getResourceAsStream("/knight/crouch_idle.png");
        try {
            crouchIdleImg = ImageIO.read(isCrouchIdle);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isCrouchIdle.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isRun = getClass().getResourceAsStream("/knight/Run.png");
        try {
            runImg = ImageIO.read(isRun);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isRun.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isJump = getClass().getResourceAsStream("/knight/Jump.png");
        try {
            jumpImg = ImageIO.read(isJump);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isJump.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isHealth = getClass().getResourceAsStream("/knight/Health.png");
        try {
            healthImg = ImageIO.read(isHealth);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isHealth.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isHurt = getClass().getResourceAsStream("/knight/Hurt.png");
        try {
            hurtImg = ImageIO.read(isHurt);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isHurt.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isDeath = getClass().getResourceAsStream("/knight/Death.png");
        try {
            deathImg = ImageIO.read(isDeath);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isDeath.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isClimb = getClass().getResourceAsStream("/knight/Climb.png");
        try {
            climbImg = ImageIO.read(isClimb);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isClimb.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isHanging = getClass().getResourceAsStream("/knight/Hanging.png");
        try {
            hangingImg = ImageIO.read(isHanging);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isHanging.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isSlide = getClass().getResourceAsStream("/knight/Slide.png");
        try {
            slideImg = ImageIO.read(isSlide);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isSlide.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isRoll = getClass().getResourceAsStream("/knight/Roll.png");
        try {
            rollImg = ImageIO.read(isRoll);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isRoll.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isPray = getClass().getResourceAsStream("/knight/Pray.png");
        try {
            prayImg = ImageIO.read(isPray);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isPray.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isAttack = getClass().getResourceAsStream("/knight/Attacks.png");
        try {
            attackImg = ImageIO.read(isAttack);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isAttack.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isAirAttack = getClass().getResourceAsStream("/knight/attack_from_air.png");
        try {
            airAttackImg = ImageIO.read(isAirAttack);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isAirAttack.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        InputStream isCrouchAttack = getClass().getResourceAsStream("/knight/crouch_attacks.png");
        try {
            crouchAttackImg = ImageIO.read(isCrouchAttack);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                isCrouchAttack.close();
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

        if(isRunning){
            g.drawImage(runAnimation[animationIndex], (int) moveRight, (int) moveDown, 256, 128, null);
        } else {
            g.drawImage(idleAnimation[animationIndex], (int) moveRight, (int) moveDown, 256, 128, null);
        }
    }

}
