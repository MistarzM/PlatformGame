package entities;

import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity{

    private int animationIndex, enemyState, enemyType;
    private int animationTick, animationSpeed = 25;

     public Enemy(float x, float y, int width, int height, int enemyType){
        super(x, y, width, height);
        this.enemyType = enemyType;
        hitBox = new Rectangle2D.Float(x, y, width, height);
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

    public void update(){
         updateAnimationTick();
    }

    public int getAnimationIndex(){
         return animationIndex;
    }
    public int getEnemyState(){
         return enemyState;
    }
}
