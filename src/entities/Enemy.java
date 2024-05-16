package entities;

import main.Game;
import utils.Constants;

import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;
import static utils.Constants.Direction.*;

public abstract class Enemy extends Entity{

    private int animationIndex, enemyState, enemyType;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float speedInAir;
    private float gravity = 0.02f * Game.SCALE;
    private float walkingSpeed = 0.8f * Game.SCALE;
    private int walkingDirection = LEFT;

    private int numberOfEnemyTilesWidth;
    private int numberOfEnemyTilesHeight;

     public Enemy(float x, float y, int width, int height, int enemyType, int numberOfEnemyTilesWidth, int numberOfEnemyTilesHeight){
        super(x, y, width, height);
        this.enemyType = enemyType;
        hitBox = new Rectangle2D.Float(x, y, width, height);
        this.numberOfEnemyTilesWidth = numberOfEnemyTilesWidth;
        this.numberOfEnemyTilesHeight = numberOfEnemyTilesHeight;
    }

    private void updateAnimationTick(){
         animationTick++;
         if(animationTick>=animationSpeed){
             animationTick = 0;
             animationIndex++;

             if(animationIndex>=GetSpriteAmount(enemyType, enemyState)){
                 animationIndex = 0;
             }
         }
    }

    public void update(int[][] levelData){
         updateMove(levelData);
         updateAnimationTick();
    }

    private void updateMove(int[][] levelData) {
        if(firstUpdate){
            if(!IsEntityOnFloor(hitBox, levelData)){
                inAir = true;
            }
            firstUpdate = false;
        }

        if(inAir){
            if(LegalMove(hitBox.x, hitBox.y + speedInAir, hitBox.width, hitBox.height, levelData)){
                hitBox.y += speedInAir;
                speedInAir += gravity;

            } else {
                inAir = false;
                hitBox.y = EntityAndRoofAndFloorYPositionCollision(hitBox, speedInAir, numberOfEnemyTilesHeight);
            }
        } else {
            switch(enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
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

                    break;
            }
        }
    }
    private void changeWalkingDirection(){
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
