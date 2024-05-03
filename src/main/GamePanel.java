package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utils.Constants.PlayerConstants.*;    // import all actions: IDLE, ATTACK etc.
import static utils.Constants.Direction.*;          // import direction - movement for characters

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GamePanel extends JPanel {     // JPanel -> picture

    private MouseInputs mouseInputs;
    private float xPos = 0, yPos = 0;
    private BufferedImage   idleImg, crouchIdleImg, runImg, jumpImg, healthImg,
                            hurtImg, deathImg, climbImg, hangingImg, slideImg,
                            rollImg, prayImg, attackImg, airAttackImg, crouchAttackImg;
    private BufferedImage[][] knightAnimations;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean running = false;
    private int attacking = -1;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        idleImg = importImg("/knight/Idle.png");
        crouchIdleImg = importImg("/knight/crouch_idle.png");
        runImg = importImg("/knight/Run.png");
        jumpImg = importImg("/knight/Jump.png");
        healthImg = importImg("/knight/Health.png");
        hurtImg = importImg("/knight/Hurt.png");
        deathImg = importImg("/knight/Death.png");
        climbImg = importImg("/knight/Climb.png");
        hangingImg = importImg("/knight/Hanging.png");
        slideImg = importImg("/knight/Slide.png");
        rollImg = importImg("/knight/Roll.png");
        prayImg = importImg("/knight/Pray.png");
        attackImg = importImg("/knight/Attacks.png");
        airAttackImg = importImg("/knight/attack_from_air.png");
        crouchAttackImg = importImg("/knight/crouch_attacks.png");
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

    private BufferedImage importImg(String path) {
        BufferedImage img = null;
        InputStream is = getClass().getResourceAsStream(path);
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
        return img;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void setDirection(int direction){
        this.playerDirection = direction;
        running = true;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public void setAttacking(){
        this.attacking++;
        if(attacking>=4)
            attacking = 0;
    }

    private void UpdateAnimationTick() {
        animationTick++;
        if(animationTick >= animationRefresh){
            animationTick = 0;
            animationIndex++;
            if(animationIndex>= GetSpriteAmount(playerAction)){
                animationIndex = 0;
            }
        }
    }

    private void setAnimation(){

        if(running){
            playerAction = RUN;
        } else if (attacking == 0){
            playerAction = RIGHT_ATTACK_1;
        }else if (attacking == 1){
            playerAction = RIGHT_ATTACK_2;
        }else if (attacking == 2){
            playerAction = RIGHT_ATTACK_3;
        }else if (attacking == 3){
            playerAction = RIGHT_ATTACK_4;
        } else {
            playerAction = IDLE;
        }
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);

        UpdateAnimationTick();

        setAnimation();

        g.drawImage(knightAnimations[playerAction][animationIndex], (int) xPos, (int) yPos, 256, 128, null);
    }

}
