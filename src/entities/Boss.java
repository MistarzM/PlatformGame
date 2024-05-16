package entities;

import main.Game;

import static utils.Constants.EnemyConstants.*;

public class Boss extends Enemy{

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS, NUMBER_OF_BOSS_TILES_WIDTH, NUMBER_OF_BOSS_TILES_HEIGHT);
        initHitBox(x, y, (int)(50 * 1.5 * Game.SCALE), (int)(78 * 1.5 * Game.SCALE));
    }
}
