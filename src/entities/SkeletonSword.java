package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Direction.*;

public class SkeletonSword extends Enemy {

    // attack hit boxes
    private Rectangle2D.Float attackHitBox;

    public SkeletonSword(float x, float y) {
        super(x, y, SKELETON_SWORD_WIDTH, SKELETON_SWORD_HEIGHT, SKELETON_SWORD, NUMBER_OF_SKELETON_SWORD_TILES_WIDTH, NUMBER_OF_SKELETON_SWORD_TILES_HEIGHT);
        initHitBox(x, y, (int)(15 * 1.5 * Game.SCALE), (int)(59 * 1.5 * Game.SCALE));
        initAttackHitBox();
    }

    private void initAttackHitBox() {
        attackHitBox = new Rectangle2D.Float(x, y, (int) (70 * Game.SCALE), (int) ( 40 * Game.SCALE));
    }

    private void updateAttackHitBox() {
        if(walkingDirection == RIGHT) {
            attackHitBox.x = hitBox.x + hitBox.width + (int) (Game.SCALE * 10);
        } else if (walkingDirection == LEFT) {
            attackHitBox.x = hitBox.x - hitBox.width - 3 * Game.TILE_SIZE - (int)(Game.SCALE * 10);
        }
        attackHitBox.y = hitBox.y + (Game.SCALE * 20);
    }

    public void update(int[][] levelData, Player player){
        updateAttackHitBox();

        updateMove(levelData, player);
        updateAnimationTick(SKELETON_SWORD_IDLE, SKELETON_SWORD_ATTACK_1);
    }

    private void updateMove(int[][] levelData, Player player) {
        if(firstUpdate){
            firstUpdateCheck(levelData);
        }

        if(inAir){
            updateInAir(levelData);
        } else {
            switch(enemyState) {
                case SKELETON_SWORD_IDLE:
                    updateState(SKELETON_SWORD_WALK);
                    break;
                case SKELETON_SWORD_WALK:

                    if(playerDetected(levelData, player)){
                        turnTowardsPlayer(player);
                    }
                    if(playerInAttackRange(player)){
                        updateState(SKELETON_SWORD_ATTACK_1);
                    }

                    updateMovement(levelData);

                    break;
            }
        }
    }

    public void drawAttackHitBox(Graphics g, int levelOffsetX){
       g.setColor(Color.red);
       g.drawRect((int)attackHitBox.x - levelOffsetX, (int)attackHitBox.y, (int)attackHitBox.width, (int)attackHitBox.height);
    }

    public int flipX(){
        if(walkingDirection == RIGHT){
            return 0;
        } else {
            return width - 4 * Game.TILE_SIZE;
        }
    }

    public int flipW(){
        if(walkingDirection == RIGHT){
            return 1;
        } else {
            return -1;
        }
    }

}
