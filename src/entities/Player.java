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

import static utils.Constants.ANIMATION_REFRESH;
import static utils.Constants.GRAVITY;
import static utils.Constants.PlayerConstants.*;    // import all actions: IDLE, ATTACK etc.
import static utils.Constants.Direction.*;          // import direction - movement for characters
import static utils.LoadAndSave.*;
import static utils.HelperMethods.LegalMovePlayer;
import static utils.HelperMethods.EntityAndWallXPositionCollision;
import static utils.HelperMethods.EntityAndRoofAndFloorYPositionCollision;
import static utils.HelperMethods.IsEntityOnFloor;

public class Player extends  Entity{


    private BufferedImage[][] knightAnimations;
    private boolean running = false, attacking = false;
    private boolean up, left, down, right, jump, interaction;

    private int flipX = 0;
    private int flipW = 1;

    private float speedOfJump = -5.0f * Game.SCALE;
    private float fallSpeedInCollisionCase = 0.5f * Game.SCALE;

    private int[][] levelData;

    private float xPlayerHitBox = 57 * 2 * Game.SCALE;      // *2 because we increased the size of the knight(128, 64)->(256, 128)
    private float yPlayerHitBox = 18 * 2 * Game.SCALE;
    private float widthPlayerHitBox = 19 * 2;  // 38
    private float heightPlayerHitBox = 91; // 46 * 2 -1

    // helpers for gravity and jumping
    private int numberOfPlayerTilesWidth = 3;    // because 38 < 16 (Tile_size) * 3 => 38 < 48
    private int numberOfPlayerTilesHeight = 6;   // because 91 < 16 (Tile_size) * 6 => 91 < 96

    // attack hit boxes
    private Rectangle2D.Float attackHitBox;
    private boolean attackChecked;

    // user interface -> keyboard tips
   private BufferedImage[] keyboardInteraction;
   private boolean eInteraction;

    // health bar
    private BufferedImage[] healthBar;

    private int healthBarWidth = (int) (64 * 4 * Game.SCALE);
    private int healthBarHeight = (int) (16 * 4 * Game.SCALE);
    private int healthBarX = (int) (32 * Game.SCALE);
    private int healthBarY = (int) (32 * Game.SCALE);

    private int healthImageIndex = 0;   // img[0] = full hp

    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 8;
        this.runningSpeed = 1.6f * Game.SCALE;
        this.currentHealth = maxHealth;
        loadAnimations();
        initHitBox((int)(widthPlayerHitBox), (int)(heightPlayerHitBox));
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

    public void setPlayerSpawn(Point playerSpawn){
        this.x = playerSpawn.x;
        this.y = playerSpawn.y;
        hitBox.x = x;
        hitBox.y = y;
    }

    private void checkInteraction() {
        int midTileWidth = (int)((hitBox.x + hitBox.width/2)/Game.TILE_SIZE);
        int midTileHeight = (int)((hitBox.y + hitBox.height/2)/Game.TILE_SIZE);
        if(levelData[midTileHeight][midTileWidth] == 2) {
            eInteraction = true;
            if(interaction){
                //System.out.println("Next map");
                playing.setChangeLevel(true);
            }
        } else {
            eInteraction = false;
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

    public void render(Graphics g, int xPlayerOffset, int yPlayerOffset){

        g.drawImage(knightAnimations[state][animationIndex], (int)(hitBox.x - xPlayerHitBox) - xPlayerOffset + flipX, (int)(hitBox.y-yPlayerHitBox)- yPlayerOffset, width * flipW, height, null);
        drawHitBox(g, xPlayerOffset, yPlayerOffset);
        drawAttackHitBox(g, xPlayerOffset, yPlayerOffset);
        drawHealthBar(g);
        if(eInteraction){
            drawButtonE(g, xPlayerOffset, yPlayerOffset);
        }
    }

    private void drawButtonE(Graphics g, int xPlayerOffset, int yPlayerOffset) {
        g.drawImage(keyboardInteraction[20],(int)(hitBox.x+ hitBox.width/2 - 20 * Game.SCALE) - xPlayerOffset, (int)(hitBox.y - 40 * Game.SCALE) - yPlayerOffset,(int)(40 * Game.SCALE),(int)(40 * Game.SCALE), null);
    }

    private void drawAttackHitBox(Graphics g, int xPlayerOffset, int yPlayerOffset) {
        g.setColor(Color.red);
        g.drawRect((int)attackHitBox.x - xPlayerOffset, (int)attackHitBox.y - yPlayerOffset, (int)attackHitBox.width, (int)attackHitBox.height);
    }

    private void drawHealthBar(Graphics g) {
        g.drawImage(healthBar[healthImageIndex], healthBarX, healthBarY, healthBarWidth, healthBarHeight, null);
    }

    private void UpdateAnimationTick() {
        animationTick++;
        if(animationTick >= ANIMATION_REFRESH){
            animationTick = 0;
            animationIndex++;
            if(animationIndex>= GetSpriteAmount(state)){
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation(){

        int startAnimation = state;

        if(running){
            state = RUN;
        } else {
            state = IDLE;
        }

        if(inAir){
            state = JUMP;
        }

        if(attacking){
            state = RIGHT_ATTACK_1;
            if(startAnimation != RIGHT_ATTACK_1){
                animationIndex = 1;
                animationTick = 0;
                return;
            }
        }

        if(startAnimation != state){
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
            xMovingSpeed -= runningSpeed;         // if left && right => pos = speed - speed = 0
            flipX = width + (int)(0.7 * Game.TILE_SIZE);
            flipW = -1;
        }
        if(right){
            xMovingSpeed += runningSpeed;
            flipX = 0;
            flipW = 1;
        }

        if(!inAir){
            if(!IsEntityOnFloor(hitBox, levelData)){
                inAir = true;
            }
        }

        if(inAir){
            if(LegalMovePlayer(hitBox.x, hitBox.y + speedInAir, hitBox.width, hitBox.height, levelData)){
                hitBox.y += speedInAir;
                speedInAir += GRAVITY;
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
        if(LegalMovePlayer(hitBox.x + xMovingSpeed, hitBox.y, hitBox.width ,hitBox.height, levelData)){
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

        BufferedImage keyboardImg       = LoadAndSave.GetSpriteAtlas(LoadAndSave.KEYBOARD_BUTTONS);

        knightAnimations = new BufferedImage[22][12];
        healthBar = new BufferedImage[50];
        keyboardInteraction = new BufferedImage[54];

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

        for(int i = 0; i < keyboardInteraction.length; i++){
            if(i<50) {
                healthBar[i] = healthBarImg.getSubimage(0, i * 16, 64, 16);
            }
            keyboardInteraction[i] = keyboardImg.getSubimage((i % 8) * 16, (i / 8) * 16, 16, 16);
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
        state = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, levelData)){
            inAir = true;
        }
    }
}
