package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Direction.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Boss extends Enemy{

    private Rectangle2D.Float attackHitBox;

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS, NUMBER_OF_BOSS_TILES_WIDTH, NUMBER_OF_BOSS_TILES_HEIGHT, BOSS_IDLE, BOSS_ATTACK_NO_BREATH, BOSS_DEAD);
        initHitBox(50 * 2, 78 * 2 +  2 * Game.TILE_SIZE);
        this.attackRange = 9 * Game.TILE_SIZE;
        initAttackHitBox();
    }

    private void initAttackHitBox() {
        attackHitBox = new Rectangle2D.Float(x, y, (int) (140 * Game.SCALE), (int)(80 * Game.SCALE));
    }

    private void updateAttackHitBox(){
        if(walkingDirection == RIGHT){
            attackHitBox.x = hitBox.x + attackHitBox.width - (int) (Game.SCALE * 40);
        } else if (walkingDirection == LEFT){
            attackHitBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 40);
        }
        attackHitBox.y = hitBox.y + (Game.SCALE * 100);
    }

    public void update(int[][] levelData, Player player){
        updateAttackHitBox();
        updateBossBehavior(levelData, player);
        updateAnimationTick(BOSS_ATTACK);
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
                    updateState(BOSS_FLYING);
                    break;
                case BOSS_FLYING:

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

                    if(animationIndex == 9 && !attackChecked){
                        checkPlayerHitBox(player, attackHitBox);
                    }
                    break;
                case BOSS_ATTACK_NO_BREATH:
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
            return width - 3 * Game.TILE_SIZE;
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

}
