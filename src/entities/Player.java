package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadAndSave;
import levels.LevelHandler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;    // import all actions: IDLE, ATTACK etc.
import static utils.Constants.Direction.*;          // import direction - movement for characters
import static utils.LoadAndSave.*;
import static utils.HelperMethods.LegalMove;
import static utils.HelperMethods.EntityAndWallXPositionCollision;
import static utils.HelperMethods.EntityAndRoofAndFloorYPositionCollision;
import static utils.HelperMethods.IsEntityOnFloor;

public class Player extends  Entity{


    private BufferedImage[][] knightAnimations;
    private int animationTick, animationIndex, animationRefresh = 15;
    private int playerAction = IDLE;
    private boolean running = false, attacking = false;
    private boolean up, left, down, right, jump, interaction;

    private int flipX = 0;
    private int flipW = 1;

    private float speedOfRunning = 1.4f * Game.SCALE;
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

    // attack hit boxes
    private Rectangle2D.Float attackHitBox;
    private boolean attackChecked;

    // health bar
    private BufferedImage[] healthBar;

    private int healthBarWidth = (int) (64 * 4 * Game.SCALE);
    private int healthBarHeight = (int) (16 * 4 * Game.SCALE);
    private int healthBarX = (int) (32 * Game.SCALE);
    private int healthBarY = (int) (32 * Game.SCALE);

    private int maxHealth = 8;
    private int currentHealth = maxHealth;
    private int healthImageIndex = 0;   // img[0] = full hp

    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitBox(x, y, (int)(widthPlayerHitBox), (int)(heightPlayerHitBox));
        initAttackHitBox();
    }

    private void initAttackHitBox() {
        attackHitBox = new Rectangle2D.Float(x, y, (int) (40 * Game.SCALE), (int) ( 30 * Game.SCALE));
    }

    public void update(){
        updateHealthBar();

        if(currentHealth <= 0){
            playing.setGameOver(true);
            return;
        }
        updateAttackHitBox();

        updatePosition();

        checkInteraction();

        if(attacking){
            checkAttack();
        }
        UpdateAnimationTick();
        setAnimation();
    }

    private void checkInteraction() {
        int midTileWidth = (int)((hitBox.x + hitBox.width/2)/Game.TILE_SIZE);
        int midTileHeight = (int)((hitBox.y + hitBox.height/2)/Game.TILE_SIZE);
        if(levelData[midTileHeight][midTileWidth] == 2 && interaction){
            System.out.println("Next map");
        }
    }

    private void checkAttack() {
        if(attackChecked || animationIndex != 4){
            return;
        }
        attackChecked = true;
        playing.checkEnemyHitBox(attackHitBox);
    }

    private void updateAttackHitBox() {
       if(right) {
            attackHitBox.x = hitBox.x + hitBox.width + (int) (Game.SCALE * 12);
       } else if (left) {
           attackHitBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 12);
       }
       attackHitBox.y = hitBox.y + (Game.SCALE * 30);
    }

    private void updateHealthBar() {
        healthImageIndex = 48 - (currentHealth * 6);
    }

    public void changeHealth(int value){
        currentHealth += value;

        if(currentHealth <= 0) {
            currentHealth = 0;
            healthImageIndex = 49;
            //gameOver();
        } else if( currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void render(Graphics g, int xPlayerOffset){

        g.drawImage(knightAnimations[playerAction][animationIndex], (int)(hitBox.x - xPlayerHitBox) - xPlayerOffset + flipX, (int)(hitBox.y-yPlayerHitBox), width * flipW, height, null);
        drawHitBox(g, xPlayerOffset);
        drawAttackHitBox(g, xPlayerOffset);
        drawHealthBar(g);
    }

    private void drawAttackHitBox(Graphics g, int xPlayerOffset) {
        g.setColor(Color.red);
        g.drawRect((int)attackHitBox.x - xPlayerOffset, (int)attackHitBox.y, (int)attackHitBox.width, (int)attackHitBox.height);
    }

    private void drawHealthBar(Graphics g) {
        g.drawImage(healthBar[healthImageIndex], healthBarX, healthBarY, healthBarWidth, healthBarHeight, null);
    }

    private void UpdateAnimationTick() {
        animationTick++;
        if(animationTick >= animationRefresh){
            animationTick = 0;
            animationIndex++;
            if(animationIndex>= GetSpriteAmount(playerAction)){
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
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
            if(startAnimation != RIGHT_ATTACK_1){
                animationIndex = 1;
                animationTick = 0;
                return;
            }
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

        if(!inAir){
            if((!left && !right) || (left && right)) {
                return;
            }
        }

        float xMovingSpeed = 0;

        if(left){                                   // we do not have to check left && !right because
            xMovingSpeed -= speedOfRunning;         // if left && right => pos = speed - speed = 0
            flipX = width + (int)(0.7 * Game.TILE_SIZE);
            flipW = -1;
        }
        if(right){
            xMovingSpeed += speedOfRunning;
            flipX = 0;
            flipW = 1;
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

        BufferedImage healthBarImg      = LoadAndSave.GetSpriteAtlas(LoadAndSave.HEALTH_BAR_MINIMUM_DAMAGE);

        knightAnimations = new BufferedImage[22][12];
        healthBar = new BufferedImage[50];

        for (int i = 0; i < knightAnimations[0].length; i++) {
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

        for(int i = 0; i < healthBar.length; i++){
            healthBar[i] = healthBarImg.getSubimage(0, i * 16, 64, 16);
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
    public void setInteraction(boolean interaction){
        this.interaction = interaction;
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
    public boolean getInteraction(){
        return interaction;
    }

    public void reset(){            // reset player -> position, health etc.
        resetDirectionBoolean();
        inAir = false;
        attacking = false;
        running = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, levelData)){
            inAir = true;
        }
    }
}
