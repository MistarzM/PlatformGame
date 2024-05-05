package entities;

import main.Game;
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
import static utils.HelperMethods.LegalMove;
import static utils.HelperMethods.EntityAndWallXPositionCollision;
import static utils.HelperMethods.EntityAndRoofAndFloorYPositionCollision;
import static utils.HelperMethods.IsEntityOnFloor;

public class Player extends  Entity{


    private BufferedImage[][] knightAnimations;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;
    private boolean running = false, attacking = false;
    private boolean up, left, down, right, jump;

    private float speedOfRunning = 1.2f;
    private float speedInAir = 0f;
    private float gravity = 0.1f * Game.SCALE;
    private float speedOfJump = -4.0f * Game.SCALE;
    private float fallSpeedInCollisionCase = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private int[][] levelData;

    private float xPlayerHitBox = 57 * 2 * Game.SCALE;      // *2 because we increased the size of the knight(128, 64)->(256, 128)
    private float yPlayerHitBox = 18 * 2 * Game.SCALE;
    private float widthPlayerHitBox = 19 * 2 * Game.SCALE;  // 38
    private float heightPlayerHitBox = 91 * Game.SCALE; // 46 * 2 -1

    // helpers for gravity and jumping
    private int numberOfPlayerTilesWidth = 3;    // because 38 < 16 (Tile_size) * 3 => 38 < 48
    private int numberOfPlayerTilesHeight = 6;   // because 91 < 16 (Tile_size) * 6 => 91 < 96


    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, widthPlayerHitBox, heightPlayerHitBox);
    }

    public void update(){

        updatePosition();
        UpdateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){

        g.drawImage(knightAnimations[playerAction][animationIndex], (int)(hitBox.x - xPlayerHitBox), (int)(hitBox.y-yPlayerHitBox), width, height, null);
        drawHitBox(g);
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

        if(inAir){
            playerAction = JUMP;
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

        if(jump){
            jumping();
        }

        if(!inAir && !left && !right){
            return;
        }

        float xMovingSpeed = 0;

        if(left){                                   // we do not have to check left && !right because
            xMovingSpeed -= speedOfRunning;         // if left && right => pos = speed - speed = 0
        }
        if(right){
            xMovingSpeed += speedOfRunning;
        }

        if(!inAir){
            if(!IsEntityOnFloor(hitBox, levelData)){
                inAir = true;
            }
        }

        if(inAir){
            if(LegalMove(hitBox.x, hitBox.y + speedInAir, hitBox.width, hitBox.height, levelData)){
                hitBox.y += speedInAir;
                speedInAir += gravity;
                updateXPosition(xMovingSpeed);
            } else {
                hitBox.y = EntityAndRoofAndFloorYPositionCollision(hitBox, speedInAir, numberOfPlayerTilesHeight);
                if(speedInAir > 0){
                    resetInAir();
                } else {
                    speedInAir = fallSpeedInCollisionCase;
                }
                updateXPosition(xMovingSpeed);
            }
        } else{
            updateXPosition(xMovingSpeed);
        }
        running = true;
    }

    private void jumping(){
        if(inAir){
            return;
        }

        inAir = true;
        speedInAir = speedOfJump;
    }

    private void resetInAir() {
        inAir = false;
        speedInAir = 0;
    }

    private void updateXPosition(float xMovingSpeed) {
        if(LegalMove(hitBox.x + xMovingSpeed, hitBox.y, hitBox.width ,hitBox.height, levelData)){
            hitBox.x += xMovingSpeed;
        } else {
            hitBox.x = EntityAndWallXPositionCollision(hitBox, xMovingSpeed, numberOfPlayerTilesWidth);
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

    public void loadLevelData(int[][] levelData){
        this.levelData = levelData;
        if(!IsEntityOnFloor(hitBox, levelData)){
            inAir = true;
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
    public void setJump(boolean jump){
        this.jump = jump;
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
    public boolean getJump(){
        return jump;
    }
}
