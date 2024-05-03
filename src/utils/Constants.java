package utils;

public class Constants {

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
        public static final int JUMP_ATTACK = 20;
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
                case JUMP_ATTACK:
                case CROUCH_ATTACK:
                    return 7;
                default:
                    return 1;
            }
        }
    }
}
