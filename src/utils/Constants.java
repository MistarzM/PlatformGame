package utils;

import main.Game;

public class Constants {

    public static final float GRAVITY = 0.1f * Game.SCALE;
    public static final int ANIMATION_REFRESH = 20;

    public static class EnemyConstants {

        // boss
        public static final int BOSS = 0;

        public static final int BOSS_IDLE = 0;
        public static final int BOSS_WALK = 1;
        public static final int BOSS_ATTACK = 2;
        public static final int BOSS_HURT = 3;
        public static final int BOSS_DEAD = 4;
        public static final int BOSS_CAST = 5;
        public static final int BOSS_SPELL = 6;

        public static final int BOSS_WIDTH_DEFAULT = 140;
        public static final int BOSS_HEIGHT_DEFAULT = 93;

        public static final int BOSS_WIDTH = (int) (BOSS_WIDTH_DEFAULT* 3  * Game.SCALE);
        public static final int BOSS_HEIGHT = (int) (BOSS_HEIGHT_DEFAULT * 3 * Game.SCALE);

        public static final int BOSS_DRAW_OFFSET_X = (int) (95 * 3  * Game.SCALE);
        public static final int BOSS_DRAW_OFFSET_Y = (int) (38 * 3 * Game.SCALE);

        public static final int NUMBER_OF_BOSS_TILES_WIDTH = 5;
        public static final int NUMBER_OF_BOSS_TILES_HEIGHT = 11;

        // skeleton sword
        public static final int SKELETON_SWORD = 1;

        public static final int SKELETON_SWORD_IDLE = 0;
        public static final int SKELETON_SWORD_WALK = 1;
        public static final int SKELETON_SWORD_JUMP = 2;
        public static final int SKELETON_SWORD_HIT = 3;
        public static final int SKELETON_SWORD_DEAD = 4;
        public static final int SKELETON_SWORD_ATTACK_1 = 5;
        public static final int SKELETON_SWORD_ATTACK_2 = 6;

        public static final int SKELETON_SWORD_WIDTH_DEFAULT = 128;
        public static final int SKELETON_SWORD_HEIGHT_DEFAULT = 96;

        public static final int SKELETON_SWORD_WIDTH = (int) (SKELETON_SWORD_WIDTH_DEFAULT * 1.5 * Game.SCALE);
        public static final int SKELETON_SWORD_HEIGHT = (int) (SKELETON_SWORD_HEIGHT_DEFAULT * 1.5 * Game.SCALE);

        public static final int SKELETON_SWORD_DRAW_OFFSET_X = (int) (36 * 1.5 * Game.SCALE);
        public static final int SKELETON_SWORD_DRAW_OFFSET_Y = (int) (37 * 1.5 * Game.SCALE);

        public static final int NUMBER_OF_SKELETON_SWORD_TILES_WIDTH = 2;
        public static final int NUMBER_OF_SKELETON_SWORD_TILES_HEIGHT = 6;


        public static final int GetSpriteAmount(int enemyType, int enemyState) {

            switch (enemyType) {
                case BOSS:
                    switch (enemyState) {
                        case BOSS_IDLE:
                        case BOSS_WALK:
                            return 8;
                        case BOSS_ATTACK:
                        case BOSS_DEAD:
                            return 10;
                        case BOSS_HURT:
                            return 3;
                        case BOSS_CAST:
                            return 9;
                        case BOSS_SPELL:
                            return 16;
                    }
                case SKELETON_SWORD:
                    switch (enemyState) {
                        case SKELETON_SWORD_IDLE:
                        case SKELETON_SWORD_DEAD:
                            return 4;
                        case SKELETON_SWORD_JUMP:
                        case SKELETON_SWORD_WALK:
                            return 6;
                        case SKELETON_SWORD_HIT:
                            return 3;
                        case SKELETON_SWORD_ATTACK_1:
                            return 8;
                        case SKELETON_SWORD_ATTACK_2:
                            return 11;
                    }

            }
            return 0;
        }

        public static int GetEnemyMaxHealth(int enemyType) {
            switch (enemyType) {
                case BOSS:
                    return 20;
                case SKELETON_SWORD:
                    return 4;
                default:
                    return 1;

            }
        }

        public static int GetEnemyDamage(int enemyType) {
            switch (enemyType) {
                case BOSS:
                    return 2;
                case SKELETON_SWORD:
                    return 1;
                default:
                    return 1;

            }
        }

    }

    public static class GUI {
        public static class Buttons{
            public static final int BUTTONS_WIDTH = 184;
            public static final int BUTTONS_HEIGHT = 90;
            public static final int SCALED_BUTTONS_WIDTH = (int)(BUTTONS_WIDTH * Game.SCALE * 0.8);
            public static final int SCALED_BUTTONS_HEIGHT = (int) (BUTTONS_HEIGHT  * Game.SCALE * 0.8);
        }

        public static class PauseButtons{
            public static final int SOUND_SIZE = 90;
            public static final int SCALED_SOUND_SIZE = (int)(SOUND_SIZE * Game.SCALE * 0.65);
        }

        public static class ControlButtons{
            public static final int CONTROL_SIZE = 90;
            public static final int SCALED_CONTROL_SIZE = (int)(CONTROL_SIZE * Game.SCALE * 0.65);
        }

        public static class VolumeButtons{
            public static final int VOLUME_WIDTH = 28;
            public static final int VOLUME_HEIGHT = 44;
            public static final int SLIDER_WIDTH = 215;

            public static final int SCALED_VOLUME_WIDTH = (int)(VOLUME_WIDTH * Game.SCALE );
            public static final int SCALED_VOLUME_HEIGHT = (int)(VOLUME_HEIGHT * Game.SCALE);
            public static final int SCALED_SLIDER_WIDTH = (int)(SLIDER_WIDTH * Game.SCALE);
        }
    }

    public static class Direction{
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }

    public static  class PlayerConstants{
        public static final int IDLE = 0;
        public static final int CROUCH_IDLE = 1;
        public static final int RUN = 2;
        public static final int JUMP = 3;
        public static final int HEALTH = 4;
        public static final int HURT = 5;
        public static final int DEATH = 6;
        public static final int CLIMB = 7;
        public static final int HANGING = 8;
        public static final int SLIDE = 9;
        public static final int ROLL = 10;
        public static final int PRAY = 11;
        public static final int LEFT_ATTACK_1 = 12;
        public static final int LEFT_ATTACK_2 = 13;
        public static final int LEFT_ATTACK_3 = 14;
        public static final int LEFT_ATTACK_4 = 15;
        public static final int RIGHT_ATTACK_1 = 16;
        public static final int RIGHT_ATTACK_2 = 17;
        public static final int RIGHT_ATTACK_3 = 18;
        public static final int RIGHT_ATTACK_4 = 19;
        public static final int AIR_ATTACK = 20;
        public static final int CROUCH_ATTACK = 21;

        public static int GetSpriteAmount(int playerAction){

            switch(playerAction){
                case IDLE:
                case CROUCH_IDLE:
                case RUN:
                case JUMP:
                case HEALTH:
                case HANGING:
                    return 8;
                case HURT:
                    return 3;
                case DEATH:
                case LEFT_ATTACK_2:
                case RIGHT_ATTACK_2:
                case LEFT_ATTACK_3:
                case RIGHT_ATTACK_3:
                case ROLL:
                    return 4;
                case CLIMB:
                case LEFT_ATTACK_1:
                case RIGHT_ATTACK_1:
                case LEFT_ATTACK_4:
                case RIGHT_ATTACK_4:
                    return 6;
                case SLIDE:
                    return 10;
                case PRAY:
                    return 12;
                case AIR_ATTACK:
                case CROUCH_ATTACK:
                    return 7;
                default:
                    return 1;
            }
        }
    }
}
