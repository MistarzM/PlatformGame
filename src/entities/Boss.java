package entities;

import main.Game;

import static utils.Constants.Direction.LEFT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Boss extends Enemy{

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS, NUMBER_OF_BOSS_TILES_WIDTH, NUMBER_OF_BOSS_TILES_HEIGHT);
        initHitBox(x, y, (int)(50 * 1.5 * Game.SCALE), (int)(78 * 1.5 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player){
        updateMove(levelData, player);
        updateAnimationTick(BOSS_IDLE, BOSS_ATTACK);
    }

    private void updateMove(int[][] levelData, Player player) {
        if(firstUpdate){
            firstUpdateCheck(levelData);
        }

        if(inAir){
            updateInAir(levelData);
        } else {
            switch(enemyState) {
                case BOSS_IDLE:
                    updateState(BOSS_FLYING);
                    break;
                case BOSS_FLYING:

                    if(playerDetected(levelData, player)){
                        turnTowardsPlayer(player);
                    }
                    if(playerInAttackRange(player)){
                        updateState(BOSS_ATTACK);
                    }

                    updateMovement(levelData);

                    break;
            }
        }
    }

}
