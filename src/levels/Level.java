package levels;

import entities.Boss;
import entities.SkeletonSword;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelperMethods.*;

public class Level {

    private BufferedImage bufferedImage;
    private int[][] levelData;
    private ArrayList<SkeletonSword> skeletonsSword;
    private ArrayList<Boss> bosses;

    private int levelTilesWide;
    private int maxTileOffset;
    private int maxLevelOffsetX;

    private Point playerSpawn;

    public Level(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
        createLevelData();
        addEnemies();
        calculateLevelOffset();
        findPlayerSpawn();
    }

    private void findPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(bufferedImage);
    }

    private void calculateLevelOffset() {
        levelTilesWide = bufferedImage.getWidth();
        maxTileOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILE_SIZE * maxTileOffset;
    }

    private void addEnemies() {
        skeletonsSword = GetSkeletonSword(bufferedImage);
        bosses = GetBoss(bufferedImage);

    }

    private void createLevelData() {
        levelData = GetLevelData(bufferedImage);
    }

    public int getSpriteIndex(int x, int y){
        return levelData[y][x];
    }

    public int[][] getLevelData(){
        return levelData;
    }

    public int getMaxLevelOffsetX(){
        return maxLevelOffsetX;
    }

    public ArrayList<SkeletonSword> getSkeletonsSword(){
        return skeletonsSword;
    }

    public ArrayList<Boss> getBosses(){
        return bosses;
    }

    public Point getLevelSpawn(){
        return playerSpawn;
    }
}
