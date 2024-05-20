package entities;

import main.Game;
import utils.Constants;

import java.awt.geom.Rectangle2D;

import static utils.Constants.ANIMATION_REFRESH;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.GRAVITY;
import static utils.HelperMethods.*;
import static utils.Constants.Direction.*;

public abstract class Enemy extends Entity{

    protected int enemyType;
    protected  boolean firstUpdate = true;
    protected int walkingDirection = LEFT;
    protected int enemyTileY;
    protected float attackRange;

    protected int numberOfEnemyTilesWidth;
    protected int numberOfEnemyTilesHeight;

    protected boolean alive = true;
    protected boolean attackChecked;

    private int enemyIdle, enemyHurt, enemyDead;

     public Enemy(float x, float y, int width, int height, int enemyType, int numberOfEnemyTilesWidth, int numberOfEnemyTilesHeight, int enemyIdle, int enemyHurt, int enemyDead){
        super(x, y, width, height);
        this.enemyType = enemyType;
        hitBox = new Rectangle2D.Float(x, y, width, height);
        this.runningSpeed = 0.4f * Game.SCALE;
        this.numberOfEnemyTilesWidth = numberOfEnemyTilesWidth;
        this.numberOfEnemyTilesHeight = numberOfEnemyTilesHeight;
        maxHealth = GetEnemyMaxHealth(enemyType);
        currentHealth = maxHealth;
        this.enemyIdle = enemyIdle;
        this.enemyHurt = enemyHurt;
        this.enemyDead = enemyDead;
    }

    protected void firstUpdateCheck(int[][] levelData){
        if(firstUpdate) {
            if (!IsEntityOnFloor(hitBox, levelData)) {
                inAir = true;
            }
            firstUpdate = false;
        }
    }

    protected void updateInAir(int[][] levelData){
        if(inAir){
            if(LegalMove(hitBox.x, hitBox.y + speedInAir, hitBox.width, hitBox.height, levelData)){
                hitBox.y += speedInAir;
                speedInAir += GRAVITY;

            } else {
                inAir = false;
                hitBox.y = EntityAndRoofAndFloorYPositionCollision(hitBox, speedInAir, numberOfEnemyTilesHeight);
                enemyTileY = (int) (hitBox.y / Game.TILE_SIZE);
            }
        }
    }

    protected void updateMovement(int[][] levelData){
        float xSpeed = 0;

        if(walkingDirection == LEFT){
            xSpeed = -runningSpeed;
        } else {
            xSpeed = runningSpeed;
        }

        if(LegalMove(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)){
            if(IsFloor(hitBox, xSpeed, levelData)) {
                hitBox.x += xSpeed;
                return;
            }
        }
        changeWalkingDirection();

    }
    protected void turnTowardsPlayer(Player player){
         if(player.hitBox.x < hitBox.x){
             walkingDirection = LEFT;
         } else {
             walkingDirection = RIGHT;
         }
    }

    protected void updateState(int enemyState){
         this.state = enemyState;
         animationTick = 0;
         animationIndex = 0;
    }

    protected boolean playerDetected(int[][] levelData, Player player){
            int playerTileY = (int)(player.getHitBox().y / Game.TILE_SIZE);
            for(int i = 0; i < 6 ;i++) {                                    // player height(6 tiles) check
                for(int j = 0; j < numberOfEnemyTilesHeight; j++) {         // enemy height (all tiles) check
                    if (playerTileY + i == enemyTileY + j) {
                        if (playerInPatrolRange(player)) {
                            if (NoObstaclesBetween(levelData, hitBox, player.hitBox, enemyTileY + j)) {
                                //System.out.println("detected");
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
    }

    protected boolean playerInPatrolRange(Player player) {
         int distanceBetween = (int) Math.abs(player.hitBox.x - hitBox.x);

         return distanceBetween <= attackRange * 6;
    }

    protected boolean playerInAttackRange(Player player){
        int distanceBetween = (int) Math.abs(player.hitBox.x + player.hitBox.width/2 - (hitBox.x + hitBox.width/2));

        return distanceBetween <= attackRange;
    }

    public void hurt(int damage){
         currentHealth -=damage;
         if(currentHealth <= 0){
             updateState(enemyDead);
         } else {
             updateState(enemyHurt);
         }
    }

    protected void checkPlayerHitBox(Player player, Rectangle2D.Float attackHitBox){
        if(attackHitBox.intersects(player.hitBox)){
            player.changeHealth(-GetEnemyDamage(enemyType));
        }
        attackChecked = true;

    }


    protected void changeWalkingDirection(){
        if(walkingDirection == LEFT){
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

     public boolean isAlive(){
         return alive;
    }

    public void resetEnemy(){
         hitBox.x  = x;
         hitBox.y = y;
         firstUpdate = true;
         currentHealth = maxHealth;
         updateState(enemyIdle);
         alive = true;
         speedInAir = 0;
    }
}
