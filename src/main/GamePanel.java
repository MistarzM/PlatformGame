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
    private BufferedImage[][] knightAnimations;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;


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
        knightAnimations = new BufferedImage[22][12];

        for(int i = 0; i < knightAnimations[i].length; i++){
            if(i<8) {
                knightAnimations[IDLE][i] = idleImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[CROUCH_IDLE][i] = crouchIdleImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[RUN][i] = runImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[JUMP][i] = jumpImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[HEALTH][i] = healthImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[HANGING][i] = hangingImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
            }
            if(i<3) {
                knightAnimations[HURT][i] = hurtImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
            }
            if(i < 4) {
                knightAnimations[DEATH][i] = deathImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[ROLL][i] = rollImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[RIGHT_ATTACK_2][i] = attackImg.getSubimage(((i + 6) % 8) * 128, ((i + 6) / 8) * 64, 128, 64);
                knightAnimations[RIGHT_ATTACK_3][i] = attackImg.getSubimage(((i + 10) % 8) * 128, ((i + 10) / 8) * 64, 128, 64);
                knightAnimations[LEFT_ATTACK_2][i] = attackImg.getSubimage(((i + 26) % 8) * 128, ((i + 26) / 8) * 64, 128, 64);
                knightAnimations[LEFT_ATTACK_3][i] = attackImg.getSubimage(((i + 30) % 8) * 128, ((i + 30) / 8) * 64, 128, 64);
            }
            if(i<6) {
                knightAnimations[CLIMB][i] = climbImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[RIGHT_ATTACK_1][i] = attackImg.getSubimage((i % 8) * 128, (i / 8) * 64, 128, 64);
                knightAnimations[RIGHT_ATTACK_4][i] = attackImg.getSubimage(((i + 14) % 8) * 128, ((i + 14) / 8) * 64, 128, 64);
                knightAnimations[LEFT_ATTACK_1][i] = attackImg.getSubimage(((i + 20) % 8) * 128, ((i + 20) / 8) * 64, 128, 64);
                knightAnimations[LEFT_ATTACK_4][i] = attackImg.getSubimage(((i + 34) % 8) * 128, ((i + 34) / 8) * 64, 128, 64);
            }
            if(i<10) {
                knightAnimations[SLIDE][i] = slideImg.getSubimage((i % 4) * 128, (i / 4) * 64, 128, 64);
            }
            if(i<7) {
                knightAnimations[AIR_ATTACK][i] = airAttackImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                knightAnimations[CROUCH_ATTACK][i] = crouchAttackImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
            }
            knightAnimations[PRAY][i] = prayImg.getSubimage((i%4) * 128, (i/4) * 64, 128, 64);
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
            if(animationIndex>= GetSpriteAmount(RIGHT_ATTACK_4)){
                animationIndex = 0;
            }
        }
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);

        UpdateAnimationTick();


        g.drawImage(knightAnimations[RIGHT_ATTACK_4][animationIndex], (int) moveRight, (int) moveDown, 256, 128, null);
    }

}
