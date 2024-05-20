package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.ANIMATION_REFRESH;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Direction.*;

public class SkeletonSword extends Enemy {

    // attack hit boxes
    private Rectangle2D.Float attackHitBox;
    private int attackNumber;

    public SkeletonSword(float x, float y) {
        super(x, y, SKELETON_SWORD_WIDTH, SKELETON_SWORD_HEIGHT, SKELETON_SWORD, NUMBER_OF_SKELETON_SWORD_TILES_WIDTH, NUMBER_OF_SKELETON_SWORD_TILES_HEIGHT, SKELETON_SWORD_IDLE, SKELETON_SWORD_HIT, SKELETON_SWORD_DEAD);
        initHitBox((int)(15 * 1.5), (int)(59 * 1.5));
        this.attackRange = 5 * Game.TILE_SIZE;
        initAttackHitBox();
        attackNumber = 0;
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

        updateSkeletonBehavior(levelData, player);
        updateAnimationTick();
    }

    private void updateSkeletonBehavior(int[][] levelData, Player player) {
        if(firstUpdate){
            firstUpdateCheck(levelData);
        }

        if(inAir){
            updateInAir(levelData);
        } else {
            switch(state) {
                case SKELETON_SWORD_IDLE:
                    updateState(SKELETON_SWORD_WALK);
                    break;
                case SKELETON_SWORD_WALK:

                    if(playerDetected(levelData, player)){
                        turnTowardsPlayer(player);
                        if(playerInAttackRange(player)){
                            if(attackNumber == 0) {
                                updateState(SKELETON_SWORD_ATTACK_1);
                                attackNumber++;
                            } else if (attackNumber == 1){
                                updateState(SKELETON_SWORD_ATTACK_2);
                                attackNumber--;
                            }
                        }
                    }
                    updateMovement(levelData);

                    break;
                case SKELETON_SWORD_ATTACK_1:
                    if(animationIndex == 0){
                        attackChecked = false;
                    }

                    if(animationIndex == 4 && !attackChecked){
                        checkPlayerHitBox(player, attackHitBox);
                    }
                    break;
                case SKELETON_SWORD_ATTACK_2:
                    if(animationIndex == 0){
                        attackChecked = false;
                    }
                    if(animationIndex==3){
                        attackChecked = false;
                    }

                    if((animationIndex == 2 || animationIndex == 7) && !attackChecked){
                        checkPlayerHitBox(player, attackHitBox);
                    }
                    break;

                case SKELETON_SWORD_HIT:
                    break;
            }
        }
    }

    public void drawAttackHitBox(Graphics g, int levelOffsetX, int levelOffsetY){
       g.setColor(Color.red);
       g.drawRect((int)attackHitBox.x - levelOffsetX, (int)attackHitBox.y- levelOffsetY, (int)attackHitBox.width, (int)attackHitBox.height);
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

    protected void updateAnimationTick(){
        animationTick++;
        if(animationTick>=ANIMATION_REFRESH){
            animationTick = 0;
            animationIndex++;

            if(animationIndex>=GetSpriteAmount(enemyType, state)){
                animationIndex = 0;

                if(state == SKELETON_SWORD_ATTACK_1 || state == SKELETON_SWORD_ATTACK_2){
                    state= SKELETON_SWORD_IDLE;
                } else if (state== SKELETON_SWORD_HIT){
                    state = SKELETON_SWORD_IDLE;
                } else if ( state == SKELETON_SWORD_DEAD){
                    alive = false;
                }
            }
        }
    }

}
