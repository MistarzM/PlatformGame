package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.ANIMATION_REFRESH;
import static utils.Constants.Direction.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Boss extends Enemy{

    private Rectangle2D.Float attackHitBox;
    private int attackNumber;

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS, NUMBER_OF_BOSS_TILES_WIDTH, NUMBER_OF_BOSS_TILES_HEIGHT, BOSS_IDLE, BOSS_HURT, BOSS_DEAD);
        initHitBox(22 * 3, 54 * 3);
        this.attackRange = 7 * Game.TILE_SIZE;
        initAttackHitBox();
        attackNumber = 0;
    }

    private void initAttackHitBox() {
        attackHitBox = new Rectangle2D.Float(x, y, (int) (190 * Game.SCALE), (int)(130 * Game.SCALE));
    }

    private void updateAttackHitBox(){
        if(walkingDirection == RIGHT){
            attackHitBox.x = hitBox.x + attackHitBox.width - (int) (Game.SCALE *115);
        } else if (walkingDirection == LEFT){
            attackHitBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 130);
        }
        attackHitBox.y = hitBox.y + (Game.SCALE * 30);
    }

    public void update(int[][] levelData, Player player){
        updateAttackHitBox();
        updateBossBehavior(levelData, player);
        updateAnimationTick();
    }

    private void updateBossBehavior(int[][] levelData, Player player) {
        if(firstUpdate){
            firstUpdateCheck(levelData);
        }

        if(inAir){
            updateInAir(levelData);
        } else {
            switch(state) {
                case BOSS_IDLE:
                    updateState(BOSS_WALK);
                    break;
                case BOSS_WALK:

                    if(playerDetected(levelData, player)) {
                        turnTowardsPlayer(player);
                        if (playerInAttackRange(player)) {
                            updateState(BOSS_ATTACK);
                        }
                    }

                    updateMovement(levelData);

                    break;
                case BOSS_ATTACK:
                    if(animationIndex == 0){
                        attackChecked = false;
                    }

                    if(animationIndex == 5 && !attackChecked){
                        checkPlayerHitBox(player, attackHitBox);
                    }
                    break;
                case BOSS_HURT:
                    break;
            }
        }
    }

    public void drawAttackHitBox(Graphics g, int levelOffsetX, int levelOffsetY){
        g.setColor(Color.red);
        g.drawRect((int)attackHitBox.x - levelOffsetX, (int)attackHitBox.y - levelOffsetY, (int)attackHitBox.width, (int)attackHitBox.height);
    }

    public int flipX(){
        if(walkingDirection == RIGHT){
            return width + 14 * Game.TILE_SIZE;
        } else {
            return 0;
        }
    }

    public int flipW(){
        if(walkingDirection == RIGHT){
            return -1;
        } else {
            return 1;
        }
    }

    protected void updateAnimationTick(){
        animationTick++;
        if(animationTick>=ANIMATION_REFRESH){
            animationTick = 0;
            animationIndex++;

            if(animationIndex>=GetSpriteAmount(enemyType, state)){
                animationIndex = 0;

                if(state == BOSS_ATTACK){
                    state= BOSS_IDLE;
                } else if (state== BOSS_HURT){
                    state = BOSS_IDLE;
                } else if ( state == BOSS_DEAD){
                    alive = false;
                }
            }
        }
    }

}
