package entities;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;    // import all actions: IDLE, ATTACK etc.
import static utils.Constants.Direction.*;          // import direction - movement for characters

public class Player extends  Entity{


    private BufferedImage[][] knightAnimations;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;
    private boolean running = false;
    private boolean up, left, down, right;
    private float speedOfRunning = 1.2f;

    public Player(float x, float y){
        super(x, y);
        loadAnimations();
    }

    public void update(){

        UpdateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void render(Graphics g){

        g.drawImage(knightAnimations[playerAction][animationIndex], (int) x, (int) y, 256, 128, null);
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
        } else {
            playerAction = IDLE;
        }
    }

    private void updatePosition(){

        running = false;

        if(left && !right){
            x -= speedOfRunning;
            running = true;
        } else if(!left && right){
            x += speedOfRunning;
            running = true;
        }

        if(up && !down){
            y-= speedOfRunning;
        } else if(!up && down){
            y+= speedOfRunning;
        }
    }

    private void loadAnimations() {
        InputStream isIdle              = getClass().getResourceAsStream("/knight/Idle.png");
        InputStream isCrouchIdle        = getClass().getResourceAsStream("/knight/crouch_idle.png");
        InputStream isRun               = getClass().getResourceAsStream("/knight/Run.png");
        InputStream isJump              = getClass().getResourceAsStream("/knight/Jump.png");
        InputStream isHealth            = getClass().getResourceAsStream("/knight/Health.png");
        InputStream isHurt              = getClass().getResourceAsStream("/knight/Hurt.png");
        InputStream isDeath             = getClass().getResourceAsStream("/knight/Death.png");
        InputStream isClimb             = getClass().getResourceAsStream("/knight/Climb.png");
        InputStream isHanging           = getClass().getResourceAsStream("/knight/Hanging.png");
        InputStream isSlide             = getClass().getResourceAsStream("/knight/Slide.png");
        InputStream isRoll              = getClass().getResourceAsStream("/knight/Roll.png");
        InputStream isPray              = getClass().getResourceAsStream("/knight/Pray.png");
        InputStream isAttack            = getClass().getResourceAsStream("/knight/Attacks.png");
        InputStream isAirAttack         = getClass().getResourceAsStream("/knight/attack_from_air.png");
        InputStream isCrouchAttack      = getClass().getResourceAsStream("/knight/crouch_attacks.png");

        try {
            BufferedImage idleImg = ImageIO.read(isIdle);
            BufferedImage crouchIdleImg = ImageIO.read(isCrouchIdle);
            BufferedImage runImg = ImageIO.read(isRun);
            BufferedImage jumpImg = ImageIO.read(isJump);
            BufferedImage healthImg = ImageIO.read(isHealth);
            BufferedImage hurtImg = ImageIO.read(isHurt);
            BufferedImage deathImg = ImageIO.read(isDeath);
            BufferedImage climbImg = ImageIO.read(isClimb);
            BufferedImage hangingImg = ImageIO.read(isHanging);
            BufferedImage slideImg = ImageIO.read(isSlide);
            BufferedImage rollImg = ImageIO.read(isRoll);
            BufferedImage prayImg = ImageIO.read(isPray);
            BufferedImage attackImg = ImageIO.read(isAttack);
            BufferedImage airAttackImg = ImageIO.read(isAirAttack);
            BufferedImage crouchAttackImg = ImageIO.read(isCrouchAttack);

            knightAnimations = new BufferedImage[22][12];
            for (int i = 0; i < knightAnimations[i].length; i++) {
                if (i < 8) {
                    knightAnimations[IDLE][i] = idleImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[CROUCH_IDLE][i] = crouchIdleImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[RUN][i] = runImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[JUMP][i] = jumpImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[HEALTH][i] = healthImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[HANGING][i] = hangingImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                }
                if (i < 3) {
                    knightAnimations[HURT][i] = hurtImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                }
                if (i < 4) {
                    knightAnimations[DEATH][i] = deathImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[ROLL][i] = rollImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[RIGHT_ATTACK_2][i] = attackImg.getSubimage(((i + 6) % 8) * 128, ((i + 6) / 8) * 64, 128, 64);
                    knightAnimations[RIGHT_ATTACK_3][i] = attackImg.getSubimage(((i + 10) % 8) * 128, ((i + 10) / 8) * 64, 128, 64);
                    knightAnimations[LEFT_ATTACK_2][i] = attackImg.getSubimage(((i + 26) % 8) * 128, ((i + 26) / 8) * 64, 128, 64);
                    knightAnimations[LEFT_ATTACK_3][i] = attackImg.getSubimage(((i + 30) % 8) * 128, ((i + 30) / 8) * 64, 128, 64);
                }
                if (i < 6) {
                    knightAnimations[CLIMB][i] = climbImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[RIGHT_ATTACK_1][i] = attackImg.getSubimage((i % 8) * 128, (i / 8) * 64, 128, 64);
                    knightAnimations[RIGHT_ATTACK_4][i] = attackImg.getSubimage(((i + 14) % 8) * 128, ((i + 14) / 8) * 64, 128, 64);
                    knightAnimations[LEFT_ATTACK_1][i] = attackImg.getSubimage(((i + 20) % 8) * 128, ((i + 20) / 8) * 64, 128, 64);
                    knightAnimations[LEFT_ATTACK_4][i] = attackImg.getSubimage(((i + 34) % 8) * 128, ((i + 34) / 8) * 64, 128, 64);
                }
                if (i < 10) {
                    knightAnimations[SLIDE][i] = slideImg.getSubimage((i % 4) * 128, (i / 4) * 64, 128, 64);
                }
                if (i < 7) {
                    knightAnimations[AIR_ATTACK][i] = airAttackImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                    knightAnimations[CROUCH_ATTACK][i] = crouchAttackImg.getSubimage((i % 2) * 128, (i / 2) * 64, 128, 64);
                }
                knightAnimations[PRAY][i] = prayImg.getSubimage((i % 4) * 128, (i / 4) * 64, 128, 64);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                isIdle.close();
                isCrouchIdle.close();
                isRun.close();
                isJump.close();
                isHealth.close();
                isHurt.close();
                isDeath.close();
                isClimb.close();
                isHanging.close();
                isSlide.close();
                isRoll.close();
                isPray.close();
                isAttack.close();
                isAirAttack.close();
                isCrouchAttack.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void setUp(boolean up){
        this.up = up;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public void setDown(boolean down){
        this.down = down;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public boolean getUp(){
         return up;
    }
    public boolean getLeft(){
         return left;
    }
    public boolean getDown(){
         return down;
    }
    public boolean getRight(){
        return right;
    }
}
