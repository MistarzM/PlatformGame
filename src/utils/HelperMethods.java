package utils;

import main.Game;

public class HelperMethods {

    public static boolean LegalMove(float x, float y, float width, float height, int[][] levelData){        // checks if the player can move on (without object collisions)

        if(IsLegalMovement(x, y, levelData)) {
            if(IsLegalMovement(x + width, y + height, levelData)) {
                if(IsLegalMovement(x + width, y, levelData)){
                    if(IsLegalMovement(x, y + height, levelData)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsLegalMovement(float x, float y, int[][] levelData){
        if(x < 0 || x >= Game.PANEL_WIDTH){
            return false;
        }
        if(y < 0 || y >= Game.PANEL_HEIGHT){
            return false;
        }

        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        int valueInLevelData = levelData[(int) yIndex][(int) xIndex];

        if(valueInLevelData != 1) {
            return false;
        }

        return true;
    }
}
