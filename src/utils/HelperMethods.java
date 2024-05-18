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
        int maxWidth = levelData[0].length * Game.TILE_SIZE;
        if(x < 0 || x >= maxWidth){
            return false;
        }
        if(y < 0 || y >= Game.PANEL_HEIGHT){
            return false;
        }

        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        return TileCanBePassed((int) xIndex, (int)yIndex, levelData);
    }

    public static boolean TileCanBePassed(int xTile, int yTile, int[][] levelData){

        int valueInLevelData = levelData[yTile][xTile];

        if(valueInLevelData != 1) {
            return false;
        }

        return true;
    }

    public static float EntityAndWallXPositionCollision(Rectangle2D.Float hitBox, float xMovingSpeed, int numberOfPlayerTilesWidth){

        int actualTile = (int) (hitBox.x / Game.TILE_SIZE);

        if(xMovingSpeed <= 0){       // <- left
            return actualTile * Game.TILE_SIZE;
        } else {                    // -> right
            int tileXPosition = actualTile * Game.TILE_SIZE;        //  actual tile position (left-top corner of this tile)
            int xOffset = (int)(numberOfPlayerTilesWidth * Game.TILE_SIZE - hitBox.width);     // distance between player and the start of tile
            return tileXPosition + xOffset -1;                      // we subtract one from the result because we don't want to have a character "in the wall"
        }
    }

    public static float EntityAndRoofAndFloorYPositionCollision(Rectangle2D.Float hitBox, float speedInAir, int numberOfPlayerTilesHeight){
        int actualTile = (int) (hitBox.y / Game.TILE_SIZE);

        if (speedInAir <= 0) {   //  jumping -> because we decrease y position, so - value
            return actualTile * Game.TILE_SIZE;
        } else {                // falling -> + value, because we increase y, we are go down
            int tileYPosition = actualTile * Game.TILE_SIZE;
            int yOffset = (int)(numberOfPlayerTilesHeight * Game.TILE_SIZE - hitBox.height);
            return tileYPosition + yOffset -1;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] levelData){
        if(IsLegalMovement(hitBox.x, hitBox.y + hitBox.height + 1, levelData)) {
            if(IsLegalMovement(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData)){
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] levelData){
        if(xSpeed > 0) {
            return !IsLegalMovement(hitBox.x + xSpeed + hitBox.width, hitBox.y + hitBox.height + 1, levelData);
        } else {
            return !IsLegalMovement(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, levelData);
        }
    }

    public static boolean DistanceBetweenCanBePassed(int xStart, int xEnd, int yTile, int[][] levelData){
        for(int i = 0; i < xEnd - xStart; i++) {
            if (!TileCanBePassed(xStart + i, yTile, levelData)) {
                return false;
            }
            if (TileCanBePassed(xStart + i, yTile + 1, levelData)){
                return false;
            }
        }
        return true;
    }

    public static boolean NoObstaclesBetween(int [][] levelData, Rectangle2D.Float firstHitBox,Rectangle2D.Float secondHitBox, int yTile){
        int firstTileX =(int)(firstHitBox.x / Game.TILE_SIZE);
        int secondTileX =(int)(secondHitBox.x / Game.TILE_SIZE);

        if(firstTileX > secondTileX){
            return DistanceBetweenCanBePassed(secondTileX, firstTileX, yTile, levelData);
        } else {
            return DistanceBetweenCanBePassed(firstTileX, secondTileX, yTile, levelData);
        }
    }
}
