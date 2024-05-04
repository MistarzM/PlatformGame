package entities;

import utils.LoadAndSave;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;    // import all actions: IDLE, ATTACK etc.
import static utils.Constants.Direction.*;          // import direction - movement for characters
import static utils.LoadAndSave.*;
import static main.Game.TILE_SIZE;

public class Player extends  Entity{


    private BufferedImage[][] knightAnimations;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;
    private boolean running = false, attacking = false;
    private boolean up, left, down, right;
    private float speedOfRunning = 1.2f;

    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimations();
    }

    public void update(){

        updatePosition();
        UpdateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){

        g.drawImage(knightAnimations[playerAction][animationIndex], (int) x, (int) y, width, height, null);
    }

    private void UpdateAnimationTick() {
        animationTick++;
        if(animationTick >= animationRefresh){
            animationTick = 0;
            animationIndex++;
            if(animationIndex>= GetSpriteAmount(playerAction)){
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation(){

        int startAnimation = playerAction;

        if(running){
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        if(attacking){
            playerAction = RIGHT_ATTACK_1;
        }

        if(startAnimation != playerAction){
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
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

        BufferedImage idleImg           = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_IDLE);
        BufferedImage crouchIdleImg     = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_CROUCH_IDLE);
        BufferedImage runImg            = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_RUN);
        BufferedImage jumpImg           = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_JUMP);
        BufferedImage healthImg         = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_HEALTH);
        BufferedImage hurtImg           = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_HURT);
        BufferedImage deathImg          = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_DEATH);
        BufferedImage climbImg          = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_CLIMB);
        BufferedImage hangingImg        = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_HANGING);
        BufferedImage slideImg          = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_SLIDE);
        BufferedImage rollImg           = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_ROLL);
        BufferedImage prayImg           = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_PRAY);
        BufferedImage attackImg         = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_ATTACKS);
        BufferedImage airAttackImg      = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_AIR_ATTACK);
        BufferedImage crouchAttackImg   = LoadAndSave.GetSpriteAtlas(LoadAndSave.KNIGHT_CROUCH_ATTACK);

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
    }

    public void resetDirectionBoolean(){
        up = false;
        left = false;
        down = false;
        right = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
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
