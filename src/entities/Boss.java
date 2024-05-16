package entities;

import static utils.Constants.EnemyConstants.*;

public class Boss extends Enemy{
    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS);
    }
}
