package entities;

import main.Game;
import utils.Constants;

import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;
import static utils.Constants.Direction.*;

public abstract class Enemy extends Entity{

    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected  boolean firstUpdate = true;
    protected boolean inAir;
    protected float speedInAir;
    protected float gravity = 0.02f * Game.SCALE;
    protected float walkingSpeed = 0.8f * Game.SCALE;
    protected int walkingDirection = LEFT;

    protected int numberOfEnemyTilesWidth;
    protected int numberOfEnemyTilesHeight;

     public Enemy(float x, float y, int width, int height, int enemyType, int numberOfEnemyTilesWidth, int numberOfEnemyTilesHeight){
        super(x, y, width, height);
        this.enemyType = enemyType;
        hitBox = new Rectangle2D.Float(x, y, width, height);
        this.numberOfEnemyTilesWidth = numberOfEnemyTilesWidth;
        this.numberOfEnemyTilesHeight = numberOfEnemyTilesHeight;
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
                speedInAir += gravity;

            } else {
                inAir = false;
                hitBox.y = EntityAndRoofAndFloorYPositionCollision(hitBox, speedInAir, numberOfEnemyTilesHeight);
            }
        }
    }

    protected void updateMovement(int[][] levelData){
        float xSpeed = 0;

        if(walkingDirection == LEFT){
            xSpeed = -walkingSpeed;
        } else {
            xSpeed = walkingSpeed;
        }

        if(LegalMove(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)){
            if(IsFloor(hitBox, xSpeed, levelData)) {
                hitBox.x += xSpeed;
                return;
            }
        }
        changeWalkingDirection();

    }

    protected void updateAnimationTick(){
         animationTick++;
         if(animationTick>=animationSpeed){
             animationTick = 0;
             animationIndex++;

             if(animationIndex>=GetSpriteAmount(enemyType, enemyState)){
                 animationIndex = 0;
             }
         }
    }

    protected void changeWalkingDirection(){
        if(walkingDirection == LEFT){
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

    public int getAnimationIndex(){
         return animationIndex;
    }

    public int getEnemyState(){
         return enemyState;
    }
}
