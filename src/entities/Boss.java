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

    public void update(int[][] levelData){
        updateMove(levelData);
        updateAnimationTick();
    }

    private void updateMove(int[][] levelData) {
        if(firstUpdate){
            firstUpdateCheck(levelData);
        }

        if(inAir){
            updateInAir(levelData);
        } else {
            switch(enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                  updateMovement(levelData);

                    break;
            }
        }
    }

}
