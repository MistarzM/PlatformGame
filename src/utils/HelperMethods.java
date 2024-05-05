package utils;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

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

    public static float EntityAndWallXPositionCollision(Rectangle2D.Float hitBox, float xMovingSpeed){

        int actualTile = (int) (hitBox.x / Game.TILE_SIZE);

        if(xMovingSpeed < 0){       // <- left
            return actualTile * Game.TILE_SIZE;
        } else {                    // -> right
            int tileXPosition = actualTile * Game.TILE_SIZE;        //  actual tile position (left-top corner of this tile)
            int xOffset = (int)(Game.TILE_SIZE - hitBox.width);     // distance between player and the start of tile
            return tileXPosition + xOffset -1;                      // we subtract one from the result because we don't want to have a character "in the wall"
        }
    }

    public static float EntityAndRoofAndFloorYPositionCollision(Rectangle2D.Float hitBox, float speedInAir){
        int actualTile = (int) (hitBox.y / Game.TILE_SIZE);

        if (speedInAir < 0) {   //  jumping -> because we decrease y position, so - value
            return actualTile * Game.TILE_SIZE;
        } else {                // falling -> + value, because we increase y, we are go down
            int tileYPosition = actualTile * Game.TILE_SIZE;
            int yOffset = (int)(Game.TILE_SIZE - hitBox.height);
            return tileYPosition + yOffset -1;
        }
    }
}
