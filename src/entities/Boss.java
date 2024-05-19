package entities;

import main.Game;

import static utils.Constants.Direction.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Boss extends Enemy{

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS, NUMBER_OF_BOSS_TILES_WIDTH, NUMBER_OF_BOSS_TILES_HEIGHT, BOSS_IDLE);
        initHitBox(50 * 2, 78 * 2 +  2 * Game.TILE_SIZE);
    }

    public void update(int[][] levelData, Player player){
        updateBossBehavior(levelData, player);
        updateAnimationTick(BOSS_ATTACK, BOSS_ATTACK_NO_BREATH, BOSS_IDLE);
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
            }
        }
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
