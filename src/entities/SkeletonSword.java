package entities;

import main.Game;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.EnemyConstants.BOSS_FLYING;

public class SkeletonSword extends Enemy {

    public SkeletonSword(float x, float y) {
        super(x, y, SKELETON_SWORD_WIDTH, SKELETON_SWORD_HEIGHT, SKELETON_SWORD, NUMBER_OF_SKELETON_SWORD_TILES_WIDTH, NUMBER_OF_SKELETON_SWORD_TILES_HEIGHT);
        initHitBox(x, y, (int)(15 * 1.5 * Game.SCALE), (int)(59 * 1.5 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player){
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

}
